package org.uqbar.arena.bootstrap;

/**
 * 
 * Clase que se encarga de inicializar el juego de datos de la aplicación.
 * 
 * @author fernando
 *
 */
public interface Bootstrap {

	/**
	 * Método que crea el juego de datos
	 */
	abstract void run();
	
	/**
	 *  Indica si debe ejecutarse el método run.
	 *  En un esquema temporal por default es true. 
	 *  En un esquema persistente dependerá de que el juego de datos exista o no.  
	 */
	abstract boolean isPending();
	
}
