package org.uqbar.arena.tests.transactional;

import org.uqbar.commons.utils.TransactionalAndObservable;

@TransactionalAndObservable
public class FakeObject {
	
	private String name;
	
	public FakeObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
