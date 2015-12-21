package org.uqbar.lacar.ui.model.transformer;

import org.uqbar.arena.bindings.Transformer;

/**
 *  
 * 
 * @author jfernandes
 */
public class NotNullTransformer implements Transformer<Object, Boolean> {

	@Override
	public Object viewToModel(Boolean valueFromView) {
		//throw new UnsupportedOperationException("This adapter cannot be used in the view to model direction");
		// si tiramos la exception, se rompe
		return null;
	}

	@Override
	public Boolean modelToView(Object valueFromModel) {
		return valueFromModel != null;
	}

	@Override
	public Class<Object> getModelType() {
		return Object.class;
	}

	@Override
	public Class<Boolean> getViewType() {
		return Boolean.class;
	}
	
}