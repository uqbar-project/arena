package org.uqbar.ui.jface.view;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public abstract class DialogAdapter<T, U> extends SelectionAdapter {
	private T parentModel;

	public DialogAdapter(T model) {
		this.parentModel = model;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		AdaptableDialog<U> dialog = this.createDialog();

		dialog.setModel(this.getDialogModel());
		dialog.setListener(this);
		dialog.open();
	}

	public T getParentModel() {
		return this.parentModel;
	}

	protected abstract U getDialogModel();

	protected abstract AdaptableDialog<U> createDialog();

	public void okPressed(U model) {
	}

}
