package org.uqbar.arena.examples.controls;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.ObservableObject;

/**
 * @author jfernandes
 */
public class Factura extends ObservableObject {
	private List<ItemFactura> items = new ArrayList<ItemFactura>();
	
	public List<ItemFactura> getItems() {
		return this.items;
	}
	
	public void addItem(ItemFactura item) {
		this.items.add(item);
	}

}
