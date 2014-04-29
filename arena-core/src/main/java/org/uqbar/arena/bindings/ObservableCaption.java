package org.uqbar.arena.bindings;

import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.bindings.AbstractViewObservable;
import org.uqbar.lacar.ui.model.builder.traits.WithCaption;

/**
 * Creates a binding to observe a button's "caption".
 * 
 * @author jfernandes
 */
public class ObservableCaption<V extends Widget, C extends WithCaption> extends AbstractViewObservable<V, C> {

	public ObservableCaption(V view) {
		super(view);
	}

	@Override
	public BindingBuilder createBinding(C control) {
		return control.observeCaption();
	}

}
