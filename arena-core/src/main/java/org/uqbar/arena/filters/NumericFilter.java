package org.uqbar.arena.filters;

import org.uqbar.arena.widgets.TextInputEvent;

public class NumericFilter implements TextFilter {

	@Override
	public boolean accept(TextInputEvent event) {
		return event.getPotentialTextResult().matches("[0-9,.]*");
	}

}
