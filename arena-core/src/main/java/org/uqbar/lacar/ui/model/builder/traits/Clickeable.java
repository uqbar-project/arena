package org.uqbar.lacar.ui.model.builder.traits;

import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ControlBuilder;

/**
 * A trait for clickeable components.
 * 
 * @author jfernandes
 */
public interface Clickeable<T extends ControlBuilder> {

	/**
	 * Defines the action to be done when the button is clicked, by establishing an observer on it.
	 * 
	 * @param action An {@link Action} that will be executed when the button is clicked.
	 */
	public T onClick(Action action);
	
}
