package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.builder.StyledControlBuilder;

/**
 * Control que permite ingresar una contraseña
 * 
 * @author dodain
 */
public class PasswordField extends TextBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordField(Panel container) {
		super(container);
	}
	
	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		StyledControlBuilder passwordField = container.addPasswordField();
		this.configureSkineableBuilder(passwordField);
		return passwordField;
	}

}
