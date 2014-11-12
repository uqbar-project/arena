package org.uqbar.lacar.ui.model.bindings;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;

/**
 * A transformer that has a set of values associated with keys.
 * Values are of course, the object to return as result of transforming.
 * "keys" are the input object to match.
 * Like a "switch-case" impl for transformers (very limited).
 * 
 * It has the {@link #when(Object, Object)} method to chain invocations
 * and make it look more idiomatic.
 * 
 * @author nnydejesus
 */
public class CaseTransformer<T, U> implements Transformer<T, U> {
	private final Map<T, U> mappingValues = new HashMap<T, U>();

	public CaseTransformer<T, U> when(T modelValue, U viewValue) {
		mappingValues.put(modelValue, viewValue);
		return this;
	}

	public Map<T, U> getMappingValues() {
		return mappingValues;
	}

	@Override
	public U transform(T element) {
		return mappingValues.get(element);
	}
}
