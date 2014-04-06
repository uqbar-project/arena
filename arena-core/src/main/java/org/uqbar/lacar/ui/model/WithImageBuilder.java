package org.uqbar.lacar.ui.model;

import org.uqbar.arena.graphics.Image;

import com.uqbar.commons.collections.Transformer;

/**
 * @author jfernandes
 */
public interface WithImageBuilder extends ControlBuilder {

	public <M> BindingBuilder observeImage(Transformer<M, Image> transformer);
	
}
