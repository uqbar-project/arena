package com.uqbar.objecttransactions.decorator;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.aop.transaction.utils.BasicTaskOwner;
import com.uqbar.common.transaction.TaskOwner;
import com.uqbar.objecttransactions.House;
import com.uqbar.objecttransactions.ObjectTransactionTestCase;
import com.uqbar.objecttransactions.Person;

/**
 * -Djava.system.class.loader=com.uqbar.apo.APOClassLoader
 * 
 * @author jfernandes
 */
public class ObjectTransactionTestCaseWithDecorator {
	private static Log logger = LogFactory.getLog(ObjectTransactionTestCase.class);

	/**
	 * Simple test case with a single nested transaction showing
	 * the isolation between parent & child transaction.
	 * With a commit at the end of the nested transaction.
	 */
//	@Test
	public void testWithJustOneDoorKeeper() {
		TaskOwner testCaseOwner = new BasicTaskOwner("testCaseOwner");
		House house = new House();
		
		DoorKeeper doorKeeper = new BlockingThreadDoorKeeper(new DefaultDoorKeeper(house));
		
		this.logHouseState(house, testCaseOwner);
		doorKeeper.beginTransaction();
		this.logHouseState(house, testCaseOwner);
		
		doorKeeper.openFrontDoor();
		this.logHouseState(house, testCaseOwner);
		
		doorKeeper.openBackDoor();
		this.logHouseState(house, testCaseOwner);
		
		doorKeeper.commitTransaction();
		this.logHouseState(house, testCaseOwner);
	}
	
	/**
	 * Simple test case with a single nested transaction showing
	 * the isolation between parent & child transaction.
	 * With a commit at the end of the nested transaction.
	 */
	@Test
	public void testOneDoorKeeperChangingCollectionAttribute() {
		TaskOwner testCaseOwner = new BasicTaskOwner("testCaseOwner");
		ObjectTransactionManager.begin(testCaseOwner);
		House house = new House();
		
		DoorKeeper doorKeeper = new BlockingThreadDoorKeeper(new DefaultDoorKeeper(house));
		
		doorKeeper.beginTransaction();
		doorKeeper.enterHouse(new Person("John Doe"));
		assertEquals(1, house.getPersons().size());
		
		doorKeeper.enterHouse(new Person("Foo Bar"));
		assertEquals(2, house.getPersons().size());
		
		// COMMIT & ASSERT
		doorKeeper.commitTransaction();
		assertEquals(2, house.getPersons().size());
	}
	
	protected void logHouseState(final House house, TaskOwner owner) {
		String message = "\t\t[ Tx: " + ObjectTransactionManager.getTransaction().getId() + "] " + "front="
			+ (house.isFrontDoorClosed() ? "CLOSED" : "OPENED") + " back="
			+ (house.isBackDoorClosed() ? "CLOSED" : "OPENED")
			+ " people=" + house.getPersons();
		logger.debug(message);
	}
	
}
