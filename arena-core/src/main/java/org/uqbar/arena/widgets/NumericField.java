package org.uqbar.arena.widgets;

import org.uqbar.arena.filters.NumericFilter;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.builder.StyledControlBuilder;

/**
 * Control que permite ingresar números (con o sin decimales)
 * 
 * @author dodain
 */
public class NumericField extends TextBox {

	private static final long serialVersionUID = -8972155338019962191L;
	
	private boolean withDecimals;

	public NumericField(Panel container) {
		this(container, true);
	}

	public NumericField(Panel container, boolean withDecimals) {
		super(container);
		this.withDecimals = withDecimals;
	}
	
	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		StyledControlBuilder numericField = container.addNumericField(withDecimals);
		this.withFilter(new NumericFilter(withDecimals));
		this.configureSkineableBuilder(numericField);
		return numericField;
	}

}