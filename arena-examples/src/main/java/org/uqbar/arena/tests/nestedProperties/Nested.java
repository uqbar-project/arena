package org.uqbar.arena.tests.nestedProperties;

import org.uqbar.commons.utils.Observable;

@Observable
public class Nested {
	private String second;

	public Nested(String second) {
		this.second = second;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
}
