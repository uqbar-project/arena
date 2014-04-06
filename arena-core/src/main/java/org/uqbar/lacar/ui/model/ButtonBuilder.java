package org.uqbar.lacar.ui.model;


/**
 * Extension of {@link ControlBuilder} specific for configurating buttons.
 * 
 * @author npasserini
 */
public interface ButtonBuilder extends SkinnableBuilder, WithImageBuilder {

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

	/**
	 * Defines the action to be done when the button is clicked, by establishing an observer on it.
	 * 
	 * @param action An {@link Action} that will be executed when the button is clicked.
	 */
	public abstract ButtonBuilder onClick(Action action);
	
	/**
	 * Starts a binding creation by returning a BindingBuilder
	 * to bind the button's "caption" with something else (probably a
	 * model's property).
	 */
	public BindingBuilder observeCaption();

}