package org.uqbar.lacar.ui.model.bindings;

import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.WidgetBuilder;

/**
 * base class for viewobservables
 * 
 * @author jfernandes
 */
public abstract class AbstractViewObservable<V extends Widget,C extends WidgetBuilder> implements ViewObservable<V, C> {
	private V view;
	
	public AbstractViewObservable(V view) {
		this.view = view;
	}

	@Override
	public V getView() {
		return this.view;
	}

}
