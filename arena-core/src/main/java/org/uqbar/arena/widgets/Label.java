package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.LabelBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * Representa una porción texto que se muestra dentro de un {@link Panel} y que no puede ser modificado por el
 * usuario.
 * 
 * @author npasserini
 */
public class Label extends SkinnableControl {
	private String text;

	public Label(Panel container) {
		super(container);
	}

	/**
	 * Configura el texto que debe mostrar este {@link Label}
	 * 
	 * @param text
	 */
	public Label setText(String text) {
		this.text = text;
		return this;
	}
	

	// ********************************************************
	// ** Inter-widget communication
	// ********************************************************

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		LabelBuilder labelBuilder = container.addLabel();
		
		this.configureSkineableBuilder(labelBuilder);
		
		if (this.text != null) {
			labelBuilder.setText(this.text);
		}
		
		return labelBuilder;
	}


}
