package org.uqbar.arena.bindings;

import org.uqbar.arena.widgets.Control;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.bindings.AbstractViewObservable;

/**
 * Observa el valor de un {@link Control}
 * 
 * @author npasserini
 */
public class ObservableValue<V extends Control, C extends ControlBuilder> extends AbstractViewObservable<V,C> {

	public ObservableValue(V view) {
		super(view);
	}

	@Override
	public BindingBuilder createBinding(C control) {
		return control.observeValue();
	}

}
