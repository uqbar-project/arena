package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * A single-selection list of options represented
 * as a list of radiobuttons.
 * 
 * @author jfernandes
 */
public class RadioSelector<T> extends Selector<T> {
	private static final long serialVersionUID = 7910967193422875127L;

	public RadioSelector(Container container) {
		super(container);
	}
	
	public RadioSelector(Container container, String itemsProperty) {
		super(container);
		this.bindItemsToProperty(itemsProperty);
	}

	protected ControlBuilder createBuilder(PanelBuilder container) {
		ListBuilder<Object> listBuilder = container.addRadioSelector();
		if (this.onSelection != null) {
			listBuilder.onSelection(this.onSelection);
		}
		return listBuilder;
	}
	
}