package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.uqbar.lacar.ui.model.Action;

/**
 * Sacar esto. Que poque ya existe el bindSelection
 * Lo saco en otro momento.
 */
public class SelectionChangeListener implements ISelectionChangedListener {
	private Action onSelection;

	public SelectionChangeListener(Action onSelection) {
		this.onSelection = onSelection;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		StructuredSelection selection = (StructuredSelection) event.getSelection();
		if (!selection.isEmpty()) {
			onSelection.execute();
		}
	}
}
