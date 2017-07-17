package org.uqbar.arena.aop.windows;

import org.uqbar.arena.aop.potm.MonitorApplicationModel
import org.uqbar.arena.aop.potm.ObjectTransactionImplObservable
import org.uqbar.arena.aop.potm.PureObjectTransactionMonitorWindow
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.aop.transaction.ObjectTransactionManager
import org.uqbar.common.transaction.ObjectTransaction
import org.uqbar.common.transaction.TaskOwner
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.lacar.ui.model.Action
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.Window
import org.uqbar.commons.model.exceptions.UserException

abstract class TransactionalDialog[T](owner: WindowOwner, model: T) extends Dialog[T](owner, model) with TaskOwner with ActionExecuter {
  var transaction: ObjectTransaction = null

  ObjectTransactionManager.begin(this)
  var inTransaction: Boolean = true

  def visibleMonitor() = false

  override def getName(): String = this.getTitle()

  override def getTransaction(): ObjectTransaction = this.transaction

  override def isTransactional(): Boolean = true

  override def setTransaction(transaction: ObjectTransaction): Unit = this.transaction = transaction

  override def cancel(): Unit = {
    this.inTransaction = false
    this.rollback()
    super.cancel()
  }

  override def close(): Unit = {
    super.close()
    if (this.inTransaction) this.rollback()
  }

  override def accept(): Unit = {
    doTransactionally(executeTask)
    commit
    close
  }

  def execute(target: Object, methodName: String): Action =
    new Action() {
      override def execute(): Unit = doTransactionally(() => call(target, methodName))
    }

  def doTransactionally[A](transactionBody:() => A): A = {
    try {
      ObjectTransactionManager.begin(this)
      val result = transactionBody()
      ObjectTransactionManager.commit(this)
      return result
    } catch {
      case e: Exception =>
        ObjectTransactionManager.rollback(this)
        throw new UserException(e.getMessage(), e)
    }
  }

  def rollback(): Unit = {
    ObjectTransactionManager.rollback(this)
    this.inTransaction = false
  }

  def commit(): Unit = {
    ObjectTransactionManager.commit(this)
    this.inTransaction = false
  }

  def openMonitor(): Unit =
    new PureObjectTransactionMonitorWindow(owner, new MonitorApplicationModel()).open();

}
