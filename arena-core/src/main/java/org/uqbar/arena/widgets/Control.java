package org.uqbar.arena.widgets;

import java.util.Collection;
import java.util.List;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.ObservableValue;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

import com.uqbar.commons.collections.Closure;
import com.uqbar.commons.collections.CollectionFactory;

/**
 * A {@link Widget} that allows to edit a single value. Superclass for all of the most common widgets: text
 * boxes, selectors, etc.
 * 
 * @author npasserini
 */
public abstract class Control extends Widget {
	private Collection<Binding<ControlBuilder>> bindings = CollectionFactory.createCollection();
	protected List<Closure<ControlBuilder>> configurations = CollectionFactory.createList();

	public Control(Container container) {
		super(container);
	}

	// ********************************************************
	// ** Configurations
	// ********************************************************

	// Para evitar este SupressWarnings habría que poner un self-bound generic type parameter a todos los
	// widgets y prefiero no hacerlo.
	@SuppressWarnings("unchecked")
	public Control addConfiguration(Closure<? extends ControlBuilder> configuration) {
		this.configurations.add((Closure<ControlBuilder>) configuration);
		return this;
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	/**
	 * Binds the value hold by this control with a property of the model of the container. Shortcut to
	 * {@link #bindValue(ObservableProperty)}.
	 * 
	 * @param modelProperty The name of a bindable property (getter/setter) in the model of the container.
	 * @return this
	 */
	public <C extends ControlBuilder> Binding<C> bindValueToProperty(String modelProperty) {
		return this.bindValue(new ObservableProperty(modelProperty));
	}

	/**
	 * Binds the value hold by this control with an {@link ObservableProperty}
	 * 
	 * @param modelObservable an {@link ObservableProperty}
	 * @return this
	 */
	public <C extends ControlBuilder> Binding<C> bindValue(ObservableProperty modelObservable) {
		return this.addBinding(modelObservable, new ObservableValue<C>());
	}

	public <C extends ControlBuilder> Binding<C> bindEnabled(Observable modelObservable) {
		return this.addBinding(modelObservable, new ViewObservable<C>() {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeEnabled();
			}
		});
	}

	/**
	 * Binds the "enabled" property of this control to a property of the model of the container. Shortcut to
	 * {@link #bindEnabled(ObservableProperty)}.
	 * 
	 * @param modelProperty The name of a bindable property (getter/setter) in the model of the container.
	 * @return this
	 */
	public <C extends ControlBuilder> Binding<C> bindEnabledToProperty(String propertyName) {
		return this.bindEnabled(new ObservableProperty(propertyName));
	}

	public <C extends ControlBuilder> Binding<C> bindVisible(Observable modelObservable) {
		return this.addBinding(modelObservable, new ViewObservable<C>() {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeVisible();
			}
		});
	}
	
	public <C extends ControlBuilder> Binding<C> bindVisibleToProperty(String propertyName) {
		return this.bindVisible(new ObservableProperty(propertyName));
	}

	/**
	 * Adds a binding betweeen two observables, validating them in this context.
	 * 
	 * @param model An observable property associated to a model.
	 * @param view An observable characteristic of this control.
	 * @return A {@link Binding} that allows to configure further the creating binding between view and model.
	 */
	protected <C extends ControlBuilder> Binding<C> addBinding(Observable model, ViewObservable<C> view) {
		model.setContainer(this.getContainer());

		return this.addBinding(new Binding<C>(model, view));
	}

	@SuppressWarnings("unchecked")
	public <C extends ControlBuilder> Binding<C> addBinding(Binding<C> binding) {
		this.bindings.add((Binding<ControlBuilder>) binding);
		return binding;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************

	@Override
	public void showOn(PanelBuilder container) {
		ControlBuilder builder = this.createBuilder(container);

		for (Closure<ControlBuilder> configuration : this.configurations) {
			configuration.execute(builder);
		}

		this.configure(builder);

		for (Binding<ControlBuilder> binding : this.bindings) {
			binding.execute(builder);
		}

		builder.pack();
	}

	public void configure(ControlBuilder builder) {

	}

	protected abstract ControlBuilder createBuilder(PanelBuilder container);

	public Control setWidth(final int preferredSize) {
		this.configurations.add(new Closure<ControlBuilder>() {
			@Override
			public void execute(ControlBuilder builder) {
				builder.setWidth(preferredSize);
			}
		});
		return this;
	}

	public Control setHeigth(final int preferredSize) {
		this.configurations.add(new Closure<ControlBuilder>() {
			@Override
			public void execute(ControlBuilder builder) {
				builder.setHeigth(preferredSize);
			}
		});

		return this;
	}
}