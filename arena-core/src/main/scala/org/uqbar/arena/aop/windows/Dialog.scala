package org.uqbar.arena.aop.windows

import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.arena.Application

/**
 * Ventana (SimpleWindow) especial que implementa la interface {@link TaskWindow}.
 *
 * Se utiliza para modelar una interaccion particular donde la ventana sirve
 * para realizar una "accion", y dicha accion se puede "aceptar" o "cancelar".
 *
 * Esta ventana entonces ya implementa logica para el accept() y el cancel()
 * por ejemplo para cerrar el dialogo.
 *
 * Sin embargo no se encarga de construir nada. Es decir que no construye ningun
 * elemento.
 * Las subclases se deben encargar de eso.
 *
 * @see TaskWindow
 *
 * @author npasserini
 */
class SDialog[T](owner: WindowOwner, model: T) extends SimpleWindow[T](owner, model) with DialogTrait[T] {
	val ACCEPT = "accept";
	val CANCEL = "cancel";

	override protected def createFormPanel(mainPanel: Panel) {}
	override protected def addActions(actionPanel: Panel) {}
}

class STransactionalDialog[T](owner: WindowOwner, model: T) extends SDialog[T](owner, model) with TransactionalWindowTrait[T] {

}
