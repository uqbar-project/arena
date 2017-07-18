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

	public static String RegExpWithDecimals = "-?[0-9.]+(,[0-9])?";
	public static String RegExpWithoutDecimals = "-?[0-9]+";
	
	private boolean withDecimals = true;
	
	public NumericFilter() {
		
	}
	
	public NumericFilter(boolean withDecimals) {
		this.withDecimals = withDecimals;
	}
	
	@Override
	public boolean accept(TextInputEvent event) {
		String value = event.getPotentialTextResult();
		return (value == null || value.trim().equals("") || isNumeric(value));
	}

	public boolean isNumeric(String value) {
		if (value == null) return false;
		if (withDecimals) {
			return value.matches(RegExpWithDecimals);	
		} else {
			return value.matches(RegExpWithoutDecimals);
		}
	}
	
}
