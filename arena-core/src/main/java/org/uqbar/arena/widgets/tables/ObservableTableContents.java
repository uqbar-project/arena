package org.uqbar.arena.widgets.tables;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.TableBuilder;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

/**
 * Permite establecer un binding con el contenido de una tabla, es decir, con una lista de objetos. Cada
 * elemento de esta lista será modelo de una fila de la tabla.
 * 
 * @author npasserini
 */
public class ObservableTableContents<R> implements ViewObservable<TableBuilder<R>> {

	@Override
	public BindingBuilder createBinding(TableBuilder<R> table) {
		return table.observeContents();
	}

}
