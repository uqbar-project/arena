package org.uqbar.lacar.ui.model.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;

/**
 * Representa una propiedad una vista que puede ser utilizada dentro de un binding.
 * 
 * @author npasserini
 */
public interface ViewObservable<C extends ControlBuilder> {

	/**
	 * Executes the actual binding between widget and model.
	 * 
	 * @param control
	 */
	public BindingBuilder createBinding(C control);

}
