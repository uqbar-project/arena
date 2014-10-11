package org.uqbar.lacar.ui.model;

/**
 * Objeto que sabe crear un {@link WindowBuilder} es decir
 * un objeto que representa la ventana en la tecnologia subyacente.
 * Por ejemplo swt, swing, etc.
 * 
 * @see WindowBuilder
 * 
 * @author jfernandes
 */
public interface WindowFactory {
	
	/**
	 * Creates the window
	 * @return the new window
	 */
	public WindowBuilder createWindow();

}