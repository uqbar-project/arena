package org.uqbar.lacar.ui.model.bindings;

import java.util.HashMap;
import java.util.Map;

import org.uqbar.lacar.ui.model.WidgetBuilder;

import com.uqbar.commons.collections.Transformer;

public class ControlBinding<C extends WidgetBuilder, T, U> extends Binding<C> {
	private final Map<T, U> mappingValues = new HashMap<T, U>();

	public ControlBinding(Observable model) {
		super(model, null);
	}
	
	public ControlBinding<C, T, U> when(T modelValue, U viewValue ){
		mappingValues.put(modelValue, viewValue);
		return this;
	}

	public Map<T, U> getMappingValues() {
		return mappingValues;
	}
	
	public Transformer<T, U> buildTransformer(){
		return new Transformer<T, U>() {
			@Override
			public U transform(T element) {
				return mappingValues.get(element);
			}
		};
	}
}
