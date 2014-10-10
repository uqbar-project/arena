package org.uqbar.arena.windows;

import org.uqbar.lacar.ui.model.WindowFactory;

/**
 * Representa un objeto que conoce al {@link WindowFactory} es decir a la implementacion
 * de arena para una tecnologia dada.
 * 
 * @see WindowFactory
 * 
 * @author npasserini
 */
public interface WindowOwner {

	public WindowFactory getDelegate();

}
