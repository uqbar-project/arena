package com.uqbar.aop.entities;

import java.util.ArrayList;
import java.util.List;


public class TestObject {
	
	private String name;
	private String lastName;
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public TestObject(String name, String fatherName) {
		this.name = name;
		this.lastName = fatherName;
		this.getName();
	}
	
	public void dispatch(String event){
		System.out.println("dispatch "  + event);
		for (Listener listener : listeners) {
			listener.listen(event);
		}
	}
	
	public void addListener(Listener listener){
		this.listeners.add(listener);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String nothing() {
		return getNothig();
	}
	
	public String getNothig(){
		return "nothing";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
