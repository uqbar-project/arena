package com.uqbar.apo.util;


import org.uqbar.commons.model.ObservableObject;
import org.uqbar.commons.utils.ReflectionUtils;


public class ExampleObject extends ObservableObject implements IExampleObject {

	public static final String NAME = "name";
	public static final String AGE = "age";

	private final String testRole;
	private String name;
	private Integer age;

	
	public ExampleObject(String testRole, String name, Integer age) {
		super();
		this.testRole = testRole;
		this.name = name;
		this.age = age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	/* (non-Javadoc)
	 * @see com.uqbar.objecttransactions.observable.IExmapleObject#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.setFieldValue(ExampleObject.NAME, name);
	}

	/* (non-Javadoc)
	 * @see com.uqbar.objecttransactions.observable.IExmapleObject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		String fieldValue = (String) ReflectionUtils.readField(this, ExampleObject.NAME);
		return this.testRole + " (transaction = " + this.name + ", field = " + fieldValue + ")";
	}


	@Override
	public String getTestRole() {
		return testRole;
	}
	

}