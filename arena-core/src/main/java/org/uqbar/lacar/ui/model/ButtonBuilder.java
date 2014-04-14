package org.uqbar.lacar.ui.model;

import org.uqbar.lacar.ui.model.builder.traits.Clickable;
import org.uqbar.lacar.ui.model.builder.traits.StyledWidgetBuilder;
import org.uqbar.lacar.ui.model.builder.traits.WithCaption;
import org.uqbar.lacar.ui.model.builder.traits.WithImageBuilder;


/**
 * Extension of {@link ControlBuilder} specific for configurating buttons.
 * 
 * @author npasserini
 */
public interface ButtonBuilder extends WithCaption, StyledWidgetBuilder, WithImageBuilder, Clickable {

	/**
	 * Defines this button as the default action for the window.
	 */
	public abstract ButtonBuilder setAsDefault();

	/**
	 * Binds this button to the status of the underlying task, disabling it when the validation status is not
	 * correct.
	 */
	// TODO Esto podría estar en la superclase ya que todos los controles pueden deshabilitarse.
	public abstract void disableOnError();

}