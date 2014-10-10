package com.uqbar.objecttransactions;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Transactional;

/**
 * @author jfernandes
 */
@Transactional
public class House {
	private Boolean frontDoorClosed = true;
	private Boolean backDoorClosed = true;
	private List<Person> persons = new ArrayList<Person>();
	
	public void closeFrontDoor() {
		this.frontDoorClosed = true;
	}
	
	public void openFrontDoor() {
		this.frontDoorClosed = false;
	}
	
	public void closeBackDoor() {
		this.backDoorClosed = true;
	}
	
	public void openBackDoor() {
		this.backDoorClosed = false;
	}
	
	public List<Person> getPersons() {
		return this.persons;
	}
	
	public void addPerson(Person person) {
		this.getPersons().add(person);
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Boolean isBackDoorClosed() {
		return backDoorClosed;
	}

	public void setBackDoorClosed(Boolean backDoorClosed) {
		this.backDoorClosed = backDoorClosed;
	}

	public Boolean isFrontDoorClosed() {
		return frontDoorClosed;
	}

	public void setFrontDoorClosed(Boolean frontDoorClosed) {
		this.frontDoorClosed = frontDoorClosed;
	}
}

