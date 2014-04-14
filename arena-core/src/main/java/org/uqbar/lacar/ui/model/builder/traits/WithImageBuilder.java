package org.uqbar.lacar.ui.model.builder.traits;

import org.uqbar.arena.graphics.Image;
import org.uqbar.lacar.ui.model.BindingBuilder;

import com.uqbar.commons.collections.Transformer;

/**
 * A trait for builders which can have an observable image.
 * 
 * @author jfernandes
 */
public interface WithImageBuilder {

	public <M> BindingBuilder observeImage(Transformer<M, Image> transformer);
	
}
