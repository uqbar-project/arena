package org.uqbar.lacar.ui.model;

/**
 * Representa una acción que debe tomar el sistema (interfaz o dominio) ante un evento externo, por ejemplo
 * ante una acción del usuario.
 * 
 * @author npasserini
 */
public interface Action {

	/**
	 * Ejecuta la acción definida por este objeto.
	 */
	public void execute();

}
