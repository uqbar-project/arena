package org.uqbar.lacar.ui.impl.jface.windows;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class JFaceDialogBuilder extends JFaceWindowBuilder {
	private JFaceWindowBuilder parent;

	public JFaceDialogBuilder(JFaceWindowBuilder parent) {
		this.parent = parent;
	}

	@Override
	protected Window createJFaceWindow() {
		return new Dialog(this.parent.getJFaceWindow()) {
			@Override
			protected Control createContents(Composite window) {
				return JFaceDialogBuilder.this.createWindowContents(window);
			}
			
		};
	}
}