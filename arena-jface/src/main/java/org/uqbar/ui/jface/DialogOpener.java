package org.uqbar.ui.jface;

import org.eclipse.jface.dialogs.Dialog;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class DialogOpener extends AbstractSelectionListener {
	private final Shell shell;

	public DialogOpener(Shell shell) {
		this.shell = shell;
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		new Dialog(this.shell) {
			@Override
			protected Control createContents(Composite parent) {
				return DialogOpener.this.createContents(parent);
			}

		}.open();
		
		this.afterDialog();
	}

	protected void afterDialog() {
		// Nothing by default.
	}

	protected abstract Control createContents(Composite parent);
}
