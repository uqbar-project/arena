package org.uqbar.arena.bindings;

import org.uqbar.commons.utils.Observable;

@Observable
public class ValueHolder<T> {
	public static final String VALUE = "value";
	
	private T value;

	public ValueHolder(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
	
	public void setValue(T value){
		this.value = value;
	}
}