/**
 * 
 */
package org.uqbar.arena.examples.controls;

import org.uqbar.commons.model.ObservableObject;

/**
 * @author jfernandes
 */
public class ItemFactura extends ObservableObject {
	private String producto;
	private int cantidad;
	
	public ItemFactura(String producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getProducto() {
		return producto;
	}
	
	public void setProducto(String producto) {
		this.producto = producto;
	}

}
