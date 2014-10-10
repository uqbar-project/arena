package org.uqbar.arena.aop.windows

import scala.collection.mutable.Buffer
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.TaskWindow
import org.uqbar.lacar.ui.model.Action

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
 */
trait DialogTrait[T] extends SimpleWindow[T] with TaskWindow {

  var acceptActions = Buffer[Action]();
  var cancelActions = Buffer[Action]();
  var dialogState: DialogState = _;

  // ********************************************************
  // ** Eventos
  // ********************************************************

  override def onAccept(action: Action) {
    this.acceptActions.append(action)
  }

  override def onCancel(action: Action) {
    this.cancelActions.append(action);
  }

  override def accept() {
    this.executeTask();
    this.close();
  }

  /**
   * La implementación por defecto invoca primero a {@link #cancelTask()} y luego cierra la ventana.
   */
  override def cancel() {
    this.cancelTask();
    this.close();
  }

  /**
   * Hook para ejecutar la tarea asociada a la ventana. Este método no debería tener lógica de negocio, sino
   * delegar en el modelo. Al sobrescribirlo hay que llamar al super, para no perder este comportamiento.
   *
   * @throws UserException En caso que la ejecución no sea exitosa. Eso evitará que se cierre la ventana.
   */
  protected def executeTask()  {
    this.acceptActions.foreach(action => action.execute())
    this.dialogState = ACCEPTED;
  }

  override def open() {
    this.dialogState = OPEN;
    super.open();
  }

  /**
   * Hook para ejecutar la cancelación de la tarea asociada a la ventana. Por defecto ejecuta las cancelTask que estan
   * configuradas. Al sobrescribirlo hay que llamar al super, para no perder este comportamiento.
   *
   * @throws UserException En caso que no se pueda o no se desee cancelar la tarea. Eso evitará que se
   *             cierre la ventana.
   */
  protected def cancelTask() {
    this.dialogState.doCancel(this);
  }

  def changeStateToCancelled() = this.dialogState = CANCELLED

  case object CANCELLED extends DialogState()

  case object ACCEPTED extends DialogState()

  case object OPEN extends DialogState {
    override def doCancel(dialog: DialogTrait[T]) = {
      dialog.cancelActions.foreach(actions => actions.execute())
      dialog.changeStateToCancelled()
    }
  }

  class DialogState() {
    def doCancel(dialog: DialogTrait[T]) = {}
  }

}