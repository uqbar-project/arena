package org.uqbar.arena.widgets;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.ObservableValue;
import org.uqbar.arena.widgets.traits.Sizeable;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.ControlBinding;
import org.uqbar.lacar.ui.model.bindings.Observable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

import com.uqbar.commons.collections.Closure;
import com.uqbar.commons.collections.Transformer;

/**
 * A {@link Widget} that allows to edit a single value. Superclass for all of the most common widgets: text
 * boxes, selectors, etc.
 * 
 * @author npasserini
 * @author jfernandes
 */
public abstract class Control extends Widget {

	public Control(Container container) {
		super(container);
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
	public <C extends ControlBuilder> Binding<C> bindValue(Observable modelObservable) {
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
	
	public <C extends ControlBuilder, T, U> ControlBinding <C, T, U> bindBackground(String propertyName) {
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
	
	public <C extends ControlBuilder, T, U> ControlBinding <C, T, U> bindBackgroundToTransformer(String propertyName, final Transformer<T, U> transformer) {
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
	
	public Control setWidth(final int preferredSize) {
		this.configurations.add(new Closure<WidgetBuilder>() {
			@Override
			public void execute(WidgetBuilder builder) {
				// cast horrible. Cortando refactor. Necesitamos un generic para el tipo del builder
				((Sizeable) builder).setWidth(preferredSize);
			}
		});
		return this;
	}

	public Control setHeight(final int preferredSize) {
		this.configurations.add(new Closure<WidgetBuilder>() {
			@Override
			public void execute(WidgetBuilder builder) {
				((Sizeable) builder).setHeight(preferredSize);
			}
		});

		return this;
	}
}