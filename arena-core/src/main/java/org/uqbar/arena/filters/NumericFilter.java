package org.uqbar.arena.filters;

import org.uqbar.arena.widgets.TextInputEvent;

/**
 * Numeric Filter accepts
 *  
 * - positive
 * - negative
 * - decimal (comma and point)
 * values
 * 
 */
public class NumericFilter implements TextFilter {

	@Override
	public boolean accept(TextInputEvent event) {
		return isNumeric(event.getPotentialTextResult());
	}

	public boolean isNumeric(String value) {
		return value.matches("-?[0-9.]+(,[0-9])?");
	}
	
}
