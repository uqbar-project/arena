package org.uqbar.arena.widgets;

import java.util.List;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.ObservableValue;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.ControlBinding;
import org.uqbar.lacar.ui.model.bindings.Observable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

import com.uqbar.commons.collections.Closure;
import com.uqbar.commons.collections.CollectionFactory;
import com.uqbar.commons.collections.Transformer;

/**
 * A {@link Widget} that allows to edit a single value. Superclass for all of the most common widgets: text
 * boxes, selectors, etc.
 * 
 * @author npasserini
 */
public abstract class Control extends Widget {
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
	
	public <C extends ControlBuilder, T, U> ControlBinding <C, T, U> bindBackgroud(String propertyName) {
		ObservableProperty model = new ObservableProperty(propertyName);
		model.setContainer(this.getContainer());
		final ControlBinding<C, T, U> binding = new ControlBinding<C, T, U>(model);
		binding.setView(new ViewObservable<C>() {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeBackground(binding.buildTransformer());
			}
		});
		this.addBinding(binding);
		return binding;
	}
	
	public <C extends ControlBuilder, T, U> ControlBinding <C, T, U> bindBackgroudToTransformer(String propertyName, final Transformer<T, U> transformer) {
		ObservableProperty model = new ObservableProperty(propertyName);
		model.setContainer(this.getContainer());
		final ControlBinding<C, T, U> binding = new ControlBinding<C, T, U>(model);
		binding.setView(new ViewObservable<C>() {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeBackground(transformer);
			}
		});
		this.addBinding(binding);
		return binding;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************
	
	/**
	 * Remember to call super if you override this template method.
	 */
	@Override
	public void configure(WidgetBuilder builder) {
		for (Closure<ControlBuilder> configuration : this.configurations) {
			//horrible hack to reduce the refactor: trying to move up bindings to all widgets
			// instead of just Controls.
			configuration.execute((ControlBuilder) builder);
		}
	}

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