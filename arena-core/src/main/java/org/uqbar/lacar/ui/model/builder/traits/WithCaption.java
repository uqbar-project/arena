package org.uqbar.lacar.ui.model.builder.traits;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;

/**
 * A traits for controls with observable captions.
 * 
 * @author jfernandes
 */
public interface WithCaption extends WidgetBuilder {

	/**
	 * Starts a binding creation by returning a BindingBuilder
	 * to bind the button's "caption" with something else (probably a
	 * model's property).
	 */
	public BindingBuilder observeCaption();

}
