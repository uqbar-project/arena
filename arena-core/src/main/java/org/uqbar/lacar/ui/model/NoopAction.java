package org.uqbar.lacar.ui.model;

/**
 * Implementacion de {@link Action} que no hace nada.
 * De ahí su nombre No-op ("No operation", se suele usar como convencion
 * en inglés)
 * 
 * @author jfernandes
 */
public class NoopAction implements Action {

	@Override
	public void execute() {
		// no hace nada!
	}
}
