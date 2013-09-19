package org.uqbar.arena.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

/**
 * 
 * @author npasserini
 */
public class ObservableItems<T, C extends ListBuilder<T>> implements ViewObservable<C> {
	
	@Override
	public BindingBuilder createBinding(C control) {
		return control.observeItems();
	}

}
