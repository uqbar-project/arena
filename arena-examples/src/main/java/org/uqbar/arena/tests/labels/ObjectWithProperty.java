package org.uqbar.arena.tests.labels;

import org.uqbar.commons.utils.Observable;

@Observable
public class ObjectWithProperty {
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private String nombre = "soy un texto!";

}
