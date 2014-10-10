package org.uqbar.arena.examples.controls;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

/**
 * @author jfernandes
 */
@Observable
public class Factura {
	private List<ItemFactura> items = new ArrayList<ItemFactura>();
	private String nombre;
	
	public List<ItemFactura> getItems() {
		return this.items;
	}
	
	public void addItem(ItemFactura item) {
		this.items.add(item);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void cambiarNombre() {
		this.setNombre(String.valueOf(System.currentTimeMillis()));
	}
	
}