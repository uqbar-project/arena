package org.uqbar.lacar.ui.model;

import org.uqbar.lacar.ui.model.builder.traits.SkinnableBuilder;
import org.uqbar.lacar.ui.model.builder.traits.WithImageBuilder;

/**
 * 
 * @author jfernandes
 */
public interface LabelBuilder extends SkinnableBuilder, WithImageBuilder {

	/**
	 * Se asigna un valor fijo sin binding.
	 * 
	 * TODO ¿Es necesario el {@link LabelBuilder#setText(String)}? ¿No quedaría mejor con un binding?
	 */
	public void setText(String text);


}
