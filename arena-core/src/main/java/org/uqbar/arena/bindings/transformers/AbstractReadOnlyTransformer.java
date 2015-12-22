package org.uqbar.arena.bindings.transformers;

import org.uqbar.arena.bindings.ValueTransformer;

/**
 * Abstract base class for all "read-only" {@link ValueTransformer} implementations.
 * A readonly transformer means one that is used on components just to show a value
 * and not to change it.
 * 
 * This is useful for labels, for example.
 * 
 * @author jfernandes
 */
public abstract class AbstractReadOnlyTransformer<M, V> implements ValueTransformer<M, V> {

	@Override
	public M viewToModel(V valueFromView) {
		throw new UnsupportedOperationException("This transformer cannot be used to modify the model !!!");
	}
	
}
