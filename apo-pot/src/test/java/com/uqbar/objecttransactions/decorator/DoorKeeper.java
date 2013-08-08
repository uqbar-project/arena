package com.uqbar.objecttransactions.decorator;

import com.uqbar.common.transaction.TaskOwner;
import com.uqbar.objecttransactions.House;
import com.uqbar.objecttransactions.Person;

/**
 * @author jfernandes
 */
public interface DoorKeeper {

	public void beginTransaction();
	
	public void commitTransaction();
	
	// using door
	
	public void openFrontDoor();
	
	public void openBackDoor();
	
	// populating the house

	public void enterHouse(Person person);
	
	// getters
	
	public House getHouse();

	public TaskOwner getOwner();
	
}
