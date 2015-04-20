package com.uqbar.aop.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestObject {
	
	private String name;
	private String lastName;
	private Map<String, List<Listener>> listeners = new HashMap<String, List<Listener>>();
	
	public void dispatch(String key, String event){
		if(listeners.containsKey(key)){
			for (Listener listener : listeners.get(key)) {
				listener.listen(event);
			}
		}
	}
	
	public void addListener(String key, Listener listener){
		if(!listeners.containsKey(key)){
			this.listeners.put(key, new ArrayList<Listener>());
		}
		listeners.get(key).add(listener);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName(){
		return name + " " + lastName;
	}
	
	public String description(){
		return "Hi, I'm " + getFullName();
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
