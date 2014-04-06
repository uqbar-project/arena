package org.uqbar.arena.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;
import org.uqbar.lacar.ui.model.builder.traits.WithCaption;

/**
 * Creates a binding to observe a button's "caption".
 * 
 * @author jfernandes
 */
public class ObservableCaption<C extends WithCaption> implements ViewObservable<C> {

	@Override
	public BindingBuilder createBinding(C control) {
		return control.observeCaption();
	}

}
