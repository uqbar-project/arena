package org.uqbar.arena.bindings.transformers;

import org.uqbar.arena.bindings.Transformer;

/**
 * Abstract base class for all "read-only" {@link Transformer} implementations.
 * A readonly transformer means one that is used on components just to show a value
 * and not to change it.
 * 
 * This is useful for labels, for example.
 * 
 * @author jfernandes
 */
public abstract class AbstractReadOnlyTransformer<M, V> implements Transformer<M, V> {

	@Override
	public M viewToModel(V valueFromView) {
		throw new UnsupportedOperationException("This adapter cannot be used to modify the model !!!");
	}
	
}
