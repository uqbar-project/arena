package org.uqbar.arena.examples.controls.selector;

import java.math.BigDecimal;

import org.uqbar.commons.model.ObservableObject;

/**
 * Un objeto que se puede ordenar en un restaurant.
 * Tiene un precio asociado.
 * 
 * @author jfernandes
 */
public abstract class ItemDeOrden extends ObservableObject {
	private BigDecimal precio;

	public ItemDeOrden(BigDecimal precio) {
		this.precio = precio;
	}
	
	public ItemDeOrden(String precio) {
		this(new BigDecimal(precio));
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

}
