package org.uqbar.lacar.ui.model;

import org.uqbar.arena.widgets.Widget;

/**
 * A {@link WidgetBuilder} that does nothing.
 * It's a null object.
 * 
 * Probably this is just a hack that appeared because of the refactor
 * to move up binding support for all {@link Widget} instead of just for Control's.
 * 
 * @author jfernandes
 */
public class NoopWidgetBuilder implements WidgetBuilder {
	public static final NoopWidgetBuilder SHARED_INSTANCE = new NoopWidgetBuilder();

	@Override
	public void pack() {
	}

	@Override
	public WidgetBuilder onPack(Action action) {
		return this;
	}

}
