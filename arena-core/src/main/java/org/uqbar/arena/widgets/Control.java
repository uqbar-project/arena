package org.uqbar.arena.widgets;

import org.apache.commons.collections15.Closure;
import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.ObservableValue;
import org.uqbar.arena.widgets.traits.Sizeable;
import org.uqbar.arena.widgets.traits.WidgetWithAlignment;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.lacar.ui.model.bindings.AbstractViewObservable;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

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
	 * {@link #bindValue(Observable)}.
	 * 
	 * @param modelProperty The name of a bindable property (getter/setter) in the model of the container.
	 * @return this
	 */
	public <M,C extends ControlBuilder> Binding<M,Control, C> bindValueToProperty(String modelProperty) {
		return this.bindValue(new ObservableProperty(modelProperty));
	}

	/**
	 * Binds the value hold by this control with an {@link ObservableProperty}
	 * 
	 * @param modelObservable an {@link ObservableProperty}
	 * @return this
	 */
	public <M,C extends ControlBuilder> Binding<M,Control,C> bindValue(Observable<M> modelObservable) {
		return this.addBinding(modelObservable, this.<C>value());
	}
	
	public <M,C extends ControlBuilder> Binding<M,Control,C> bindEnabled(Observable<M> modelObservable) {
		return this.addBinding(modelObservable, this.<C>enabled());
	}
	
	// ***************************
	// ** observable properties
	// ***************************
	
	public <C extends ControlBuilder> ObservableValue<Control,C> value() {
		return new ObservableValue<Control,C>(this);
	}

	public <C extends ControlBuilder> ViewObservable<Control,C> enabled() {
		return new AbstractViewObservable<Control, C>(this) {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeEnabled();
			}
		};
	}
	
	public <C extends ControlBuilder> ViewObservable<Control, C> visible() {
		return new AbstractViewObservable<Control, C>(this) {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeVisible();
			}
		};
	}
	
	public <C extends ControlBuilder> ViewObservable<Control, C> tooltip() {
		return new AbstractViewObservable<Control, C>(this) {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeTooltip();
			}
		};
	}
	
	// ***************************
	// ** bind * To *
	// ***************************
	

	/**
	 * Binds the "enabled" property of this control to a property of the model of the container. Shortcut to
	 * {@link #bindEnabled(Observable)}.
	 * 
	 * @param propertyName The name of a bindable property (getter/setter) in the model of the container.
	 * @return this
	 */
	public <C extends ControlBuilder> Binding<?,Control, C> bindEnabledToProperty(String propertyName) {
		return this.bindEnabled(new ObservableProperty(propertyName));
	}

	public <C extends ControlBuilder> Binding<?,Control, C> bindVisible(Observable modelObservable) {
		return this.addBinding(modelObservable, this.<C>visible());
	}
	
	public <C extends ControlBuilder> Binding<?,Control, C> bindVisibleToProperty(String propertyName) {
		return this.bindVisible(new ObservableProperty(propertyName));
	}

	public <C extends ControlBuilder, T, U> Binding<?,Control, C> bindBackground(Observable modelObservable) {
		return this.addBinding(modelObservable, this.<C>background());
	}

	public <C extends ControlBuilder, T, U> Binding<?,Control, C> bindBackgroundToProperty(String propertyName) {
		return this.addBinding(new ObservableProperty(propertyName), this.<C>background());
	}
	
	public <C extends ControlBuilder> ViewObservable<Control, C> background() {
		return new AbstractViewObservable<Control, C>(this) {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeBackground();
			}
		};
	}
	
	public <C extends ControlBuilder, T, U> Binding<?,Control, C> bindForegroundToProperty(String propertyName) {
		return this.addBinding(new ObservableProperty(propertyName), this.<C>foreground());
	}

	public <C extends ControlBuilder, T, U> Binding<?,Control, C> bindForeground(Observable modelObservable) {
		return this.addBinding(modelObservable, this.<C>foreground());
	}

	public <C extends ControlBuilder> ViewObservable<Control, C> foreground() {
		return new AbstractViewObservable<Control, C>(this) {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeForeground();
			}
		};
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
	
	public Control alignLeft() {
		this.configurations.add(new Closure<WidgetBuilder>() {
			@Override
			public void execute(WidgetBuilder builder) {
				((WidgetWithAlignment) builder).alignLeft();
			}
		});

		return this;
	}

	public Control alignRight() {
		this.configurations.add(new Closure<WidgetBuilder>() {
			@Override
			public void execute(WidgetBuilder builder) {
				((WidgetWithAlignment) builder).alignRight();
			}
		});

		return this;
	}

	public Control alignCenter() {
		this.configurations.add(new Closure<WidgetBuilder>() {
			@Override
			public void execute(WidgetBuilder builder) {
				((WidgetWithAlignment) builder).alignCenter();
			}
		});

		return this;
	}

}