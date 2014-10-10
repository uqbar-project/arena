package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * El valor de un {@link CheckBox} puede ser verdadero (si está seleccionado) o falso (en caso contrario).
 * 
 * @author npasserini
 */
public class CheckBox extends Control {
	
	public CheckBox(Panel container) {
		super(container);
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		return container.addCheckBox();
	}
}
