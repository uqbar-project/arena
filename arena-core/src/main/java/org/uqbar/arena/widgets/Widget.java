package org.uqbar.arena.widgets;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.uqbar.lacar.ui.model.NoopWidgetBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

import com.uqbar.commons.collections.Closure;
import com.uqbar.commons.collections.CollectionFactory;
import com.uqbar.commons.loggeable.HierarchicalLogger;
import com.uqbar.commons.loggeable.Loggeable;

/**
 * Clase abstracta de la que heredan todos los demás componentes visuales.
 * 
 * @author npasserini
 */
public class Widget implements Loggeable {
	private Collection<Binding<?, Widget, WidgetBuilder>> bindings = CollectionFactory.createCollection();
	protected List<Closure<WidgetBuilder>> configurations = CollectionFactory.createList();
	private static final long serialVersionUID = 7034829204374950200L;

	/**
	 * Map of generated indices, allows to generate default captions for labels, buttons and other widgets.
	 * For each type of widget it contains the next index to be used.
	 */
	private static Map<Class<?>, Integer> indices = CollectionFactory.createMap();

	private final Container container;

	/**
	 * Simplest {@link Widget} creation.
	 * 
	 * @param container The {@link Panel} in with Widget will be added.
	 */
	public Widget(Container container) {
		this.container = container;
		container.addChild(this);
	}
	
	@SuppressWarnings("unchecked")
	public <O> O getContainerModelObject() {
		return (O) this.getContainer().getModel().getSource();
	}

	// ********************************************************
	// ** bindings
	// ********************************************************
	
	/**
	 * Adds a binding betweeen two observables, validating them in this context.
	 * 
	 * @param model An observable property associated to a model.
	 * @param view An observable characteristic of this control.
	 * @return A {@link Binding} that allows to configure further the creating binding between view and model.
	 */
	public <M,V extends Widget, C extends WidgetBuilder> Binding<M,V,C> addBinding(Observable<M> model, ViewObservable<V,C> view) {
		model.setContainer(this.getContainer());
		return this.addBinding(new Binding<M,V,C>(model, view));
	}
	
	@SuppressWarnings("unchecked")
	public <M,V extends Widget,C extends WidgetBuilder> Binding<M, V,C> addBinding(Binding<M,V,C> binding) {
		this.bindings.add((Binding<M,Widget,WidgetBuilder>) binding);
		return binding;
	}

	// ********************************************************
	// ** Inter widget-communication
	// ********************************************************

	public Container getContainer() {
		return this.container;
	}

	/**
	 * Agrega este {@link Widget} al contenedor donde será dibujado. Una vez invocado a este método no se
	 * pueden hacer más modificaciones al {@link Widget}.
	 * 
	 * @param container El {@link PanelBuilder} que interpretará la descripción de este Widget traduciéndolas
	 *            a las instrucciones necsarias para implementarlas en la tecnología subyacente.
	 */
	public void showOn(PanelBuilder container) {
		WidgetBuilder builder = this.createBuilder(container);

		this.configure(builder);

		for (Binding<?, Widget, WidgetBuilder> binding : this.bindings) {
			binding.execute(builder);
		}

		builder.pack();
	}

	protected WidgetBuilder createBuilder(PanelBuilder container) {
		return NoopWidgetBuilder.SHARED_INSTANCE;
	}

	// ********************************************************
	// ** Utilities
	// ********************************************************

	/**
	 * Produces an automatic default autonumbered caption for any widget. The first generated caption is
	 * numbered "1".
	 */
	protected String nextCaption() {
		Class<?> cl = this.getClass();

		Integer index = Widget.indices.get(cl);
		if (index == null) {
			index = 1;
		}
		Widget.indices.put(cl, index + 1);

		return cl.getSimpleName() + index;
	}

	@Override
	public String toString() {
		return HierarchicalLogger.hierarchicalToString(this);
	}

	@Override
	public void appendYourselfTo(HierarchicalLogger visitor) {
		visitor.append(this.getClass());
	}
	
	// ********************************************************
	// ** Configurations
	// ********************************************************
	
	// Para evitar este SupressWarnings habría que poner un self-bound generic type parameter a todos los
	// widgets y prefiero no hacerlo.
	@SuppressWarnings("unchecked")
	public Widget addConfiguration(Closure<? extends WidgetBuilder> configuration) {
		this.configurations.add((Closure<WidgetBuilder>) configuration);
		return this;
	}
	
	/**
	 * Remember to call super if you override this template method.
	 */
	public void configure(WidgetBuilder builder) {
		for (Closure<WidgetBuilder> configuration : this.configurations) {
			configuration.execute(builder);
		}
	}	
}
