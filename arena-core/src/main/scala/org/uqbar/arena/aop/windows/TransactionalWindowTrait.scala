package org.uqbar.arena.aop.windows

import org.uqbar.arena.aop.potm.ObjectTransactionImplObservable
import org.uqbar.arena.aop.potm.PureObjectTransactionMonitorWindow
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.aop.potm.MonitorApplicationModel
import org.uqbar.aop.transaction.ObjectTransactionManager
import org.uqbar.common.transaction.ObjectTransaction
import org.uqbar.common.transaction.TaskOwner
import org.uqbar.lacar.ui.model.Action
import org.uqbar.commons.model.exceptions.UserException

trait TransactionalWindowTrait[T] extends DialogTrait[T] with TaskOwner with ActionExecuter {

	var transaction: ObjectTransaction = _;
	var inTransaction = true;

	ObjectTransactionManager.begin(this);

	override def createMainTemplate(mainPanel: Panel) = {
		new Button(mainPanel).setCaption("Open Monitor").onClick( new Action() {
			def execute : Unit = openMonitor()
		})
		super.createMainTemplate(mainPanel)
	}

	override def getName(): String = this.getTitle();

	override def getTransaction(): ObjectTransaction = this.transaction;

	override def isTransactional(): Boolean = true;

	override def setTransaction(transaction: ObjectTransaction) = { this.transaction = transaction };

	def execute(target: Object, methodName: String): Action = {
		return new Action() {

			override def execute() {
				try {
					ObjectTransactionManager.begin(TransactionalWindowTrait.this)
				  call(target, methodName)
					ObjectTransactionManager.commit(TransactionalWindowTrait.this)
				} catch {
					case e: Exception => {
						ObjectTransactionManager.rollback(TransactionalWindowTrait.this)
						throw new UserException(e.getMessage(), e)
					}
				}
			}
		}
	}

	override def cancel() {
		this.inTransaction = false;
		this.rollback();
		super.cancel();
	}

	override def close() {
		super.close();
		if (this.inTransaction) {
			this.rollback();
		}
	}

	override def accept() {
		try {
			ObjectTransactionManager.begin(this);
			this.inTransaction = false;
			super.accept();
			ObjectTransactionManager.commit(this);
			this.commit();
		} catch {
			case e: Exception => {
				this.inTransaction = true;
				ObjectTransactionManager.rollback(this);
				throw new UserException(e.getMessage(), e);
			}
		}

	}

	protected def rollback() {
		ObjectTransactionManager.rollback(this);
		this.inTransaction = false;
	}

	protected def commit() {
		ObjectTransactionManager.commit(this);
		this.inTransaction = false;
	}

	def openMonitor() = new PureObjectTransactionMonitorWindow(this, new MonitorApplicationModel()).open();
}