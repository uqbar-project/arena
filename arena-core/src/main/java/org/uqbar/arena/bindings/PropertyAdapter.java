package org.uqbar.arena.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ItemsBindingBuilder;

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
