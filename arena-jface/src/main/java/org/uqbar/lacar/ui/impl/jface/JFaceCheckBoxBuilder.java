package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;

/**
 * En SWT un check box se representa como un {@link Button} con un estilo especial ({@link SWT#CHECK}).
 * 
 * @author npasserini
 */
public class JFaceCheckBoxBuilder extends JFaceControlBuilder<Button> {

	public JFaceCheckBoxBuilder(Button jFaceWidget, JFaceContainer container) {
		super(container, jFaceWidget);
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeSelection(this.getWidget()));
	}
}
