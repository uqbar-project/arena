package org.uqbar.arena.examples.controls.binding.enable;

import org.uqbar.commons.utils.Observable;

/**
 * 
 * @author jfernandes
 */
@Observable
public class Country  {
	private String name;

	public Country(String nombre) {
		this.name = nombre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
