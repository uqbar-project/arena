package org.uqbar.arena.tests.nestedProperties;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.annotations.Observable;

@Observable
public class NestedPropertiesModel {
	private List<Nested> list = new ArrayList<Nested>();
	private Nested first;
	private String second;

	public Nested getFirst() {
		return first;
	}

	public void setFirst(Nested first) {
		this.first = first;
		this.second = this.first.getSecond();
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
		this.first.setSecond(second);
	}

	public List<Nested> getList() {
		return list;
	}

	public void setList(List<Nested> list) {
		this.list = list;
	}
}


