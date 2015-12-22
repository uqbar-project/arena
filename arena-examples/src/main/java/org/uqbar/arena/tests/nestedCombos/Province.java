package org.uqbar.arena.tests.nestedCombos;

import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;

@Observable
@Transactional
public class Province {
	private String name;
	private Boolean pretty;
	
	public Province(String name) {
		this.name = name;
		this.pretty = new Boolean(true);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	Boolean getPretty() {
		return pretty;
	}

	void setPretty(Boolean pretty) {
		this.pretty = pretty;
	}
	
}
