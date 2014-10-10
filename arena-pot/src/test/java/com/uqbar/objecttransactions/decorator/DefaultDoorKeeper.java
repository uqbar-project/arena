package com.uqbar.objecttransactions.decorator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.aop.transaction.utils.BasicTaskOwner;
import com.uqbar.common.transaction.TaskOwner;
import com.uqbar.objecttransactions.House;
import com.uqbar.objecttransactions.Person;

/**
 * @author jfernandes
 */
public class DefaultDoorKeeper implements DoorKeeper {
	private static Log logger = LogFactory.getLog(DefaultDoorKeeper.class);
	public House house;
	private TaskOwner owner;
	
	public DefaultDoorKeeper(House house) {
		this.house = house;
	}
	
	@Override
	public void beginTransaction() {
		this.owner = new BasicTaskOwner("keeper");
		ObjectTransactionManager.begin(owner); 
		logger.debug(">>>>> TRANSACTION CREATED!");
	}

	@Override
	public void openFrontDoor() {
		this.house.openFrontDoor(); 
		logger.debug(">>>>> FRONT DOOR OPENED IN TRANSACTION!");
	}

	@Override
	public void openBackDoor() {
		this.house.openBackDoor(); logger.debug(">>>>> BACK DOOR OPENED IN TRANSACTION!");
	}

	@Override
	public void commitTransaction() {
		ObjectTransactionManager.commit(this.owner); 
		logger.debug(">>>>> COMMITED TRANSACTION!");
	}
	
	@Override
	public void enterHouse(Person person) {
		this.house.addPerson(person);
	}
	
	// *************************
	// ** accessors
	// *************************
	
	public House getHouse() {
		return this.house;
	}
	
	public TaskOwner getOwner() {
		return this.owner;
	}

}