package org.uqbar.arena.bindings;

import org.uqbar.commons.model.IModel;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.transformer.NotNullTransformer;

/**
 * 
 * 
 * @author jfernandes
 */
public class NotNullObservable extends ObservableProperty {

	public NotNullObservable(String propertyName) {
		super(propertyName);
	}
	
	public NotNullObservable(IModel<?> model, String propertyName) {
		super(model, propertyName);
	}
	
	public NotNullObservable(Object modelObject, String propertyName) {
		super(modelObject, propertyName);
	}

	@Override
	public void configure(BindingBuilder binder) {
		super.configure(binder);
		binder.adaptWith(new NotNullTransformer());
	}

}