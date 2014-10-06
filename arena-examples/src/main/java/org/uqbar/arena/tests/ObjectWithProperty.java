package org.uqbar.arena.tests;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

@Observable
public class ObjectWithProperty {
	private List<Persona> personas = new ArrayList<Persona>();
	
	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private String nombre = "soy un texto!";

}
