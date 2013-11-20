package com.uqbar.objecttransactions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.aop.transaction.utils.BasicTaskOwner;
import com.uqbar.common.transaction.TaskOwner;

/**
 * Run with this jvm parameter!
 * 
 * -Djava.system.class.loader=com.uqbar.apo.APOClassLoader
 * 
 * @author jfernandes
 * @author npasserini
 */
public class ObjectTransactionTestCase  {
	private Object monitor = new Object();
	private RuntimeException failure;
	private static Log logger = LogFactory.getLog(ObjectTransactionTestCase.class);

	/**
	 * 
	 */
//	@Test
	public void testDifferentTransactionsSameObjectAccess() {
		TaskOwner testCaseOwner = new BasicTaskOwner("testCaseOwner");
		ObjectTransactionManager.begin(testCaseOwner);

		final House house = new House();

		Thread thread = new Thread(new SimpleTransactionExample(house));

		// first we log from the test
		this.logHouseState(house, testCaseOwner);
		thread.start(); // starts a new transaction, logs the house state and sleeps

		this.waitForTheOtherThread();
		this.logHouseState(house, testCaseOwner); // here we log the house state in our own thread.

		this.waitForTheOtherThread();
		this.logHouseState(house, testCaseOwner); // we log here

		this.waitForTheOtherThread();
		this.logHouseState(house, testCaseOwner); // we log here

		this.waitForTheOtherThread();
		this.logHouseState(house, testCaseOwner); // we log here
	}

	protected void logHouseState(final House house, TaskOwner owner) {
		logger.debug("\t[" + owner + "@" + ObjectTransactionManager.getTransaction() + "] " + "frontDoor="
			+ (house.isFrontDoorClosed() ? "CLOSED" : "OPENED") + " backdoor="
			+ (house.isBackDoorClosed() ? "CLOSED" : "OPENED"));
	}

	/**
	 * 
	 * @author jfernandes
	 */
	public final class SimpleTransactionExample implements Runnable {
		private final House house;
		public SimpleTransactionExample(House house) { this.house = house; }

		@Override
		public void run() {
			try {
				TaskOwner owner1 = new BasicTaskOwner("owner1");
				
				ObjectTransactionManager.begin(owner1); logger.debug(">>>>> TRANSACTION CREATED!");
				logHouseAndWaitForOtherThread(house, owner1);

				house.openFrontDoor(); logger.debug(">>>>> FRONT DOOR OPENED IN TRANSACTION!");
				logHouseAndWaitForOtherThread(house, owner1);

				house.openBackDoor(); logger.debug(">>>>> BACK DOOR OPENED IN TRANSACTION!");
				logHouseAndWaitForOtherThread(house, owner1);

				ObjectTransactionManager.commit(owner1); 
				logger.debug(">>>>> COMMITED TRANSACTION!");
				
				awakeTheOtherThread();
			}
			catch (RuntimeException exception) {
				threadFailed(exception);
			}
		}
	}
	
	// *******************************************************************
	// ** utility methods for handling communication between threads
	// *******************************************************************
	
	protected void waitForTheOtherThread() {
		this.awakeTheOtherThread();
		synchronized (this.monitor) {
			try {
				this.monitor.wait();
			}
			catch (InterruptedException e) {
				throw new RuntimeException("Error waiting");
			}
		}
		if (this.failure != null) {
			throw new RuntimeException("The other thread failed", this.failure);
		}
	}

	protected void threadFailed(RuntimeException failure) {
		this.failure = failure;
		this.awakeTheOtherThread();
	}

	protected void awakeTheOtherThread() {
		synchronized (monitor) {
			monitor.notifyAll();
		}
	}

	protected void logHouseAndWaitForOtherThread(House house, TaskOwner owner1) {
		logHouseState(house, owner1);
		waitForTheOtherThread();
	}
	
}