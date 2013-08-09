package org.uqbar.arena.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;

/**
 * This observable property has no reference to the model. Therefore, it can be used in places where the model
 * can change, for example in a label provider for a {@link List} or {@link Combo}.
 * 
 * @author npasserini
 */
public class ContextFreeObservableProperty extends ObservableProperty {
	private Class<?> modelType;
	
	public ContextFreeObservableProperty(Class<?> modelType, String propertyName) {
		super(propertyName);
		this.modelType = modelType;
	}

	@Override
	public void configure(BindingBuilder binder) {
		super.configure(binder);
		binder.observeProperty(this.modelType, this.propertyName);
	}

}