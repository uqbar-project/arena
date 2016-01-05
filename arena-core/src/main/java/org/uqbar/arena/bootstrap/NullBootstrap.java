package org.uqbar.arena.bootstrap;

/**
 * Implementación nula de un bootstrap que no hace nada
 * 
 * @author fernando
 *
 */
public class NullBootstrap implements Bootstrap {

	/**
	 * No hace nada
	 */
	@Override
	public void run() {
	}

	/**
	 * No está pendiente porque no hará nada
	 */
	@Override
	public boolean isPending() {
		return false;
	}

}
