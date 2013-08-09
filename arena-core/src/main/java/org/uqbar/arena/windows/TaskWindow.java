package org.uqbar.arena.windows;

import org.uqbar.lacar.ui.model.Action;

/**
 * Es una ventana que se asocia a una tarea que puede ejecutarse o cancelarse.
 * 
 * @author npasserini
 */
// TODO Tal vez sería mejor dejar las ventanas en paz y que la tarea tuviera lógica únicamente a nivel
// negocio.
public interface TaskWindow {

	/**
	 * Ejecuta la tarea asociada a esta ventana.
	 */
	public void accept();

	/**
	 * Cancela la tarea asociada a esta ventana.
	 */
	public void cancel();

	/**
	 * Permite configurar una acción que será ejecutada luego de finalizar la ejecución (exitosa) de la tarea
	 * subyacente.
	 * 
	 * @param action La acción a ejecutar.
	 */
	public void onAccept(Action action);

	/**
	 * Permite configurar una acción que será ejecutada en caso de cancelar la ejecucińo de la tarea
	 * subyacente.
	 * 
	 * @param action La acción a ejecutar.
	 */
	public void onCancel(Action action);

}
