package org.uqbar.arena.bindings;

import org.uqbar.arena.widgets.Container;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.bindings.Observable;

/** 
 * Allows to observe if any control in the container window has errors.
 *
 * @author npasserini
 */
public class NoErrorsObservable implements Observable<Object> {

	@Override
	public void setContainer(Container container) {
	}

	@Override
	public void configure(BindingBuilder binder) {
		binder.observeErrors();
	}

}
