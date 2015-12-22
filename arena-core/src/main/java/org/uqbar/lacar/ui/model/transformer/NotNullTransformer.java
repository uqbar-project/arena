package org.uqbar.lacar.ui.model.transformer;

import org.uqbar.arena.bindings.ValueTransformer;

/**
 *  
 * 
 * @author jfernandes
 */
public class NotNullTransformer implements ValueTransformer<Object, Boolean> {

	@Override
	public Object viewToModel(Boolean valueFromView) {
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