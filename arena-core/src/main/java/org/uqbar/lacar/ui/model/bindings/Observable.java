package org.uqbar.lacar.ui.model.bindings;

import org.uqbar.arena.widgets.Container;
import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.BindingBuilder;

/**
 * Representa una propiedad de un modelo o vista que puede ser utilizada dentro de un binding.
 * 
 * @author npasserini
 */
public interface Observable {

	/**
	 * Permite que este observable finalize su configuración al asignarlo a un {@link Container}.
	 * 
	 * Este también es un momento adecuado para realizar validaciones, verificando que este {@link Observable}
	 * esté siendo agregado en un contexto en el que tiene sentido.
	 * 
	 * @param container Contiene al {@link Widget} al que se asocia este binding.
	 */
	public void setContainer(Container container);

	/**
	 * Executes the actual binding between widget and model.
	 * 
	 * @param widget
	 */
	public void configure(BindingBuilder binder);

}
