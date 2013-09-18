package org.uqbar.arena.examples.controls.selector;

import org.uqbar.commons.model.ObservableObject;

/**
 * @author jfernandes
 */
public class Entrada extends ObservableObject {
	private String descripcion;

	public Entrada(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
