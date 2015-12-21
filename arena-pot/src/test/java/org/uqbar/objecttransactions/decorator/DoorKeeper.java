package org.uqbar.objecttransactions.decorator;

import org.uqbar.common.transaction.TaskOwner;
import org.uqbar.objecttransactions.House;
import org.uqbar.objecttransactions.Person;

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
