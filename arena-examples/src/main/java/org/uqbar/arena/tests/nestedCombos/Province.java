package org.uqbar.arena.tests.nestedCombos;

import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;

@Observable
@Transactional
public class Province {
	private String name;

	public Province(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
