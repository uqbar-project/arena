package org.uqbar.lacar.ui.model;

/**
 * Interfaz base para configurar un widget independientemente de la tecnología.
 * 
 * Si bien esta interfaz es independiente de la tecnología, sus implementaciones serán específicas para una
 * tecnología dada. Esto permite que componentes independientes de la tecnología puedan de todas formas
 * manipularla, a través de esta interfaz.
 * 
 * Cada implementación de esta interfaz traducirá los mensajes recibidos a las acciones que sean necesarias
 * para llevar a cabo cada una de esas acciones en la tecnología correspondiente.
 * 
 * A su vez las subinterfaces agregarán mensajes específicos para cada tipo de widget, aquí están únicamente
 * los mensajes comunes a todos los tipos de widget soportados.
 * 
 * @author npasserini
 */
public interface WidgetBuilder {

	/**
	 * Mediante este método, se indica este builder que realice las tareas finales de la construcción. No se
	 * deben agregar configuraciones posteriormente a esta indicación ya que podrían tener un comportamiento
	 * inesperado.
	 * 
	 * Normalmente este método es invocado por el contenedor de cada widget en cadena a lo largo del composite
	 * de widgets. El usuario sólo está obligado a invocarlo sobre el {@link WindowBuilder} que es la raiz de
	 * ese composite.
	 */
	public void pack();

	/**
	 * Allows to configure an action that will be executed just before the default "pack" action.
	 * 
	 * @param action The action to be executed
	 * @return this object
	 */
	public WidgetBuilder onPack(Action action);
}
