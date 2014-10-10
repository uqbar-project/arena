package org.uqbar.arena.bindings;

import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.bindings.AbstractViewObservable;

/**
 * 
 * @author npasserini
 */
public class ObservableItems<V extends Widget, T, C extends ListBuilder<T>> extends AbstractViewObservable<V, C> {
	
	public ObservableItems(V view) {
		super(view);
	}

	@Override
	public BindingBuilder createBinding(C control) {
		return control.observeItems();
	}

}
