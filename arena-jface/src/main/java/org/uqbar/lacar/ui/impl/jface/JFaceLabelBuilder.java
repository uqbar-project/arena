package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.LabelBuilder;

public class JFaceLabelBuilder extends JFaceSkinnableControlBuilder<Label> implements LabelBuilder {

	// ********************************************************
	// ** Constructors
	// ********************************************************

	public JFaceLabelBuilder(JFaceContainer container) {
		this(container, new Label(container.getJFaceComposite(), SWT.CENTER));
	}

	public JFaceLabelBuilder(JFaceContainer container, Label label) {
		super(container, label);
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	@Override
	public void setText(String text) {
		this.getWidget().setText(text);
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeText(this.getWidget()));
	}

}
