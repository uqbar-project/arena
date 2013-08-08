package com.uqbar.aop.entities;

import java.util.ArrayList;
import java.util.List;


public class TestObject {
	
	private String name;
	private String fatherName;
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public TestObject(String name, String fatherName) {
		this.name = name;
		this.fatherName = fatherName;
	}
	
	public void dispatch(String event){
		System.out.println("dispatch "  + event);
		for (Listener listener : listeners) {
			listener.listen(event);
		}
	}
	
	public void update(String name){
	}
	
	public void updateName(String name){
		this.name = name;
	}
	
	public void updateFatherName(String name){
		this.fatherName = name;
	}
	
	public void addListener(Listener listener){
		this.listeners.add(listener);
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
