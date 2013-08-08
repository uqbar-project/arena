package com.uqbar.objecttransactions.decorator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.common.transaction.TaskOwner;
import com.uqbar.objecttransactions.House;
import com.uqbar.objecttransactions.Person;

/**
 * Decorates a DoorKeeper instances in order to execute all its behavior
 * in a separated thread, blocking the caller thread.
 * 
 * @author jfernandes
 */
public class BlockingThreadDoorKeeper implements DoorKeeper {
	public static final Runnable DUMMY_RUNNABLE = new Runnable() { @Override public void run() { } };
	private static Log logger = LogFactory.getLog(DefaultDoorKeeper.class);
	private DoorKeeper decoratee;
	private DecoratorThread thread;
	private Object monitor = new Object();
	private RuntimeException failure;
	
	public BlockingThreadDoorKeeper(DoorKeeper decoratee) {
		this.decoratee = decoratee;
		this.thread = new DecoratorThread();
		this.thread.start();
	}
	
	// *******************************************************************
	// ** DoorKeeper methods
	// *******************************************************************
	
	@Override
	public void beginTransaction() {
		execute("begin", new Runnable() {
			public void run() {
				getDecoratee().beginTransaction();
			}
		});
	}

	@Override
	public void openFrontDoor() {
		execute("openFront", new Runnable() {
			public void run() {
				getDecoratee().openFrontDoor();
			}
		});
	}

	@Override
	public void openBackDoor() {
		execute("openBack", new Runnable() {
			public void run() {
				getDecoratee().openBackDoor();
			}
		});
	}

	@Override
	public void commitTransaction() {
		this.thread.die();
		execute("commit", new Runnable() {
			public void run() {
				getDecoratee().commitTransaction();
			}
		});
	}
	
	@Override
	public void enterHouse(final Person person) {
		execute("enterHouse", new Runnable() {
			public void run() {
				getDecoratee().enterHouse(person);
			}
		});
	}

	@Override
	public House getHouse() {
		return this.getDecoratee().getHouse();
	}
	
	@Override
	public TaskOwner getOwner() {
		return this.getDecoratee().getOwner();
	}
	
	// *******************************************************************
	// ** utility methods for runnables execution
	// *******************************************************************
	
	protected void execute(String methodName, Runnable runnable) {
		logger.debug("executing '" + methodName + "' in thread '" + this.thread + "'...");
		this.thread.nextRunnable(runnable);
		this.waitForTheOtherThread();
		logger.debug("woke up from '" + methodName + "' in thread '" + this.thread + "'...");
	}
	
	protected void waitForTheOtherThread() {
		this.awakeTheOtherThreadAndWait();
		this.checkFailure();
	}

	protected void checkFailure() {
		if (this.failure != null) {
			throw new RuntimeException("The other thread failed", this.failure);
		}
	}

	protected void awakeTheOtherThreadAndWait() {
		logger.trace("awaking other thread");
		synchronized (this.monitor) {
			this.monitor.notifyAll();
			try {
				this.monitor.wait();
			}
			catch (InterruptedException e) {
				throw new RuntimeException("Error waiting", e);
			}
		}
	}
	
	protected DoorKeeper getDecoratee() {
		return this.decoratee;
	}
	
	/**
	 * @author jfernandes
	 */
	public class DecoratorThread extends Thread {
		private Runnable runnable = DUMMY_RUNNABLE;
		private boolean die = false;
		
		@Override
		public void run() {
			while(true) {
				try {
					sleep(250);
				}
				catch (InterruptedException e) {
					throw new RuntimeException("Error while sleeping thread!", e);
				}
				this.runnable.run();
				if (this.die) {
					// if needs to ends, then wake up the other thread, and break the while.
					awakeTheOtherThreadAndWait();
					break;
				}
				else {
					// otherwise, wakeup and wait (keep in the while)
					logHouseAndWaitForOtherThread(decoratee.getHouse(), decoratee.getOwner());
				}
			}
		}
		
		public void die() {
			this.die = true;
		}

		protected void logHouseAndWaitForOtherThread(House house, TaskOwner owner1) {
			logHouseState(house, owner1);
			waitForTheOtherThread();
		}
		
		protected void logHouseState(final House house, TaskOwner owner) {
			String message = "\t[ Tx: " + ObjectTransactionManager.getTransaction().getId() + " ] " + "front="
				+ (house.isFrontDoorClosed() ? "CLOSED" : "OPENED") + " back="
				+ (house.isBackDoorClosed() ? "CLOSED" : "OPENED")
				+ " people=" + house.getPersons();
			logger.debug(message);
		}
		
		public void nextRunnable(Runnable runnable) {
			this.runnable = runnable;
		}
	}

}
