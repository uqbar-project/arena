package org.uqbar.arena.widgets.traits;

import org.uqbar.arena.graphics.Image;
import org.uqbar.lacar.ui.model.bindings.Binding;

import com.uqbar.commons.collections.Transformer;

/**
 * @author jfernandes
 */
public interface WidgetWithImage {

	public <M> Binding bindImageToProperty(String propertyName, Transformer<M,Image> transformer);
	
}
