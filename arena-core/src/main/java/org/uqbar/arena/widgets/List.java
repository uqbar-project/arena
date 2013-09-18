package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;


/**
 * 
 * 
 * @author npasserini
 */
//TODO: rename to ListSelector
public class List<T> extends Selector<T> {
	private static final long serialVersionUID = 1L;

	public List(Container container) {
		super(container);
	}
	
	public List(Container container, String itemsProperty) {
		super(container);
		this.bindItemsToProperty(itemsProperty);
	}
	
	// ********************************************************
	// ** Rendering
	// ********************************************************

	protected ControlBuilder createBuilder(PanelBuilder container) {
		ListBuilder<Object> listBuilder = container.addList();
		if (this.onSelection != null) {
			listBuilder.onSelection(this.onSelection);
		}
		return listBuilder;
	}

}
