package org.uqbar.arena.aop.windows

import org.uqbar.arena.actions.MessageSend

import org.uqbar.arena.aop.potm.ObjectTransactionImplObservable
import org.uqbar.arena.aop.potm.PureObjectTransactionMonitorWindow
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.commons.model.UserException
import org.uqbar.commons.utils.Observable
import org.uqbar.arena.aop.potm.MonitorApplicationModel

import com.uqbar.aop.transaction.ObjectTransactionManager
import com.uqbar.common.transaction.ObjectTransaction
import com.uqbar.common.transaction.TaskOwner

trait TransactionalWindowTrait[T] extends DialogTrait[T] with TaskOwner {

	var transaction: ObjectTransaction = _;
	var inTransaction = true;

	ObjectTransactionManager.begin(this);

	override def createMainTemplate(mainPanel: Panel) = {
		new Button(mainPanel).setCaption("Open Monitor").onClick(new MessageSend(this, "openMonitor"));
		super.createMainTemplate(mainPanel);
	}

	override def getName(): String = this.getTitle();

	override def getTransaction(): ObjectTransaction = this.transaction;

	override def isTransactional(): Boolean = true;

	override def setTransaction(transaction: ObjectTransaction) = { this.transaction = transaction };

	def execute(target: Any, methodName: String): MessageSend = {
		return new MessageSend(target, methodName) {

			override def execute() {
				try {
					ObjectTransactionManager.begin(TransactionalWindowTrait.this)
					super.execute();
					ObjectTransactionManager.commit(TransactionalWindowTrait.this)
				} catch {
					case e: Exception => {
						ObjectTransactionManager.rollback(TransactionalWindowTrait.this)
						throw new UserException(e.getMessage(), e)
					}
				}
				super.execute();

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