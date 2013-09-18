package org.uqbar.arena.examples.controls.selector;

import org.uqbar.commons.model.ObservableObject;

/**
 * 
 * @author jfernandes
 */
public class OrdenDeRestaurant extends ObservableObject {
	private Entrada entrada;
	private Plato plato;
	private Bebida bebida;

	public Entrada getEntrada() {
		return entrada;
	}
	
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	
	public Plato getPlato() {
		return plato;
	}
	
	public void setPlato(Plato plato) {
		this.plato = plato;
	}
	
	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}
	
}
