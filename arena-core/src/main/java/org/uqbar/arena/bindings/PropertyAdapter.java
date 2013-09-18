package org.uqbar.arena.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ItemsBindingBuilder;

/**
 * Adapts the model's object (to the view) by using one of its properties.
 * This is useful for example, to show a given property of an object when showing
 * it in a combo as an option. For example a Person's "fullName".
 * 
 * @author npasserini
 */
public class PropertyAdapter implements Adapter {
	private final Class<?> modelType;
	private final String propertyName;

	public PropertyAdapter(Class<?> modelType, String propertyName) {
		this.modelType = modelType;
		this.propertyName = propertyName;
	}

	@Override
	public void configure(BindingBuilder binder) {
		// TODO Cast
		((ItemsBindingBuilder) binder).adaptItemsUsingProperty(this.modelType, this.propertyName);
	}
}
