package org.uqbar.arena.widgets;

import org.apache.commons.lang.StringUtils;

/**
 * An object that receives {@link TextInputEvent} events
 * from the user and can decide whether to accept the input or not.
 * 
 * @author jfernandes
 */
public interface TextFilter {
	
	/**
	 * Returns false if you want to forbid the user's action.
	 */
	public boolean accept(TextInputEvent event);
	
	
	// innerclases - common filter implementations to reuse
	
	public final static TextFilter NUMERIC_TEXT_FILTER = new TextFilter() {
		@Override
		public boolean accept(TextInputEvent event) {
			return StringUtils.isNumeric(event.getPotentialTextResult());
		}
	};
	
	public final static TextFilter BLANK_TEXT_FILTER = new TextFilter() {
		@Override
		public boolean accept(TextInputEvent event) {
			return StringUtils.isBlank(event.getPotentialTextResult());
		}
	};

}
