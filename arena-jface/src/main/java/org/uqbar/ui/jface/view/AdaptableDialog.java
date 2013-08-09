package org.uqbar.ui.jface.view;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;

public class AdaptableDialog<T> extends Dialog {
	private T model;
	private DialogAdapter<?, T> listener;

	protected AdaptableDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected void okPressed() {
		this.listener.okPressed(this.getModel());
		super.okPressed();
	}

	public T getModel() {
		return this.model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public DialogAdapter<?, T> getListener() {
		return this.listener;
	}

	public void setListener(DialogAdapter<?, T> listener) {
		this.listener = listener;
	}
}
