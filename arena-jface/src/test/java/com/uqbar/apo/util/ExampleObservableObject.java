package com.uqbar.apo.util;

import org.uqbar.commons.utils.ReflectionUtils;
import org.uqbar.commons.utils.TransactionalAndObservable;


@TransactionalAndObservable
public class ExampleObservableObject implements IExampleObject {
	private final String testRole;
	private String name;
	private Integer age;

	public ExampleObservableObject(String testRole, String name, Integer age) {
		super();
		this.testRole = testRole;
		this.name = name;
		this.age = age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Shows the values in the current transaction, reflection should be used if
	 * field values are needed.
	 */
	@Override
	public String toString() {
		String fieldValue = (String) ReflectionUtils.readField(this, ExampleObject.NAME);
		return this.getTestRole() + " (transaction = " + this.name + ", field = " + fieldValue + ")";
	}

	@Override
	public String getTestRole() {
		return testRole;
	}

}
