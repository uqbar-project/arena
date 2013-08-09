package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ButtonBuilder;

/**
 * 
 * @author npasserini
 */
public class JFaceButtonBuilder extends JFaceSkinnableControlBuilder<Button> implements ButtonBuilder {
	
	public JFaceButtonBuilder(JFaceContainer context) {
		super(context, new Button(context.getJFaceComposite(), SWT.PUSH));
	}

	public ButtonBuilder setCaption(String caption) {
		this.getWidget().setText(caption);
		return this;
	}

	@Override
	public ButtonBuilder setAsDefault() {
		this.getWidget().getShell().setDefaultButton(this.getWidget());
		return this;
	}

	@Override
	public void disableOnError() {
		new JFaceBindingBuilder(this, SWTObservables.observeEnabled(this.getWidget()), //
			new ComputedValue() {
				@Override
				protected Object calculate() {
					return ((IStatus) JFaceButtonBuilder.this.getContainer().getStatus().getValue()).isOK();
				}
			}).build();
	}

	@Override
	public ButtonBuilder onClick(Action action) {
		this.getWidget().addSelectionListener(new JFaceActionAdapter(this.getContainer(), action));
		return this;
	}
	

	// ********************************************************
	// ** TODO Yet not implemented.
	// ********************************************************

	@Override
	public BindingBuilder observeValue() {
		throw new UnsupportedOperationException("Se intentó observar la propiedad 'value' de un Button, que no tiene dicha propiedad");
	}
}
