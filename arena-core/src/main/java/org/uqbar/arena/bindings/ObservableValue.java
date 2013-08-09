package org.uqbar.arena.bindings;

import org.uqbar.arena.widgets.Control;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

/**
 * Observa el valor de un {@link Control}
 * 
 * @author npasserini
 */
public class ObservableValue<C extends ControlBuilder> implements ViewObservable<C> {

	@Override
	public BindingBuilder createBinding(C control) {
		return control.observeValue();
	}

}
