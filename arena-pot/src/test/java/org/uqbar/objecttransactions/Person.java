package org.uqbar.objecttransactions;

import org.uqbar.commons.model.annotations.Transactional;


/**
 * @author jfernandes
 */
@Transactional
public class Person{
	private String name;

	public Person(String name) {
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
