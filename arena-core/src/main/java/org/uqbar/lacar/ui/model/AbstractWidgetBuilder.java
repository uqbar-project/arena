package org.uqbar.lacar.ui.model;

import java.util.ArrayList;

/**
 * Utility class for implementing widget builders.
 * 
 * @author npasserini
 */
public class AbstractWidgetBuilder implements WidgetBuilder {
	private ArrayList<Action> packActions = new ArrayList<Action>();

	@Override
	public void pack() {
		for (Action action : packActions) {
			action.execute();
		}
	}

	public AbstractWidgetBuilder onPack(Action action) {
		this.packActions.add(action);
		return this;
	}
}