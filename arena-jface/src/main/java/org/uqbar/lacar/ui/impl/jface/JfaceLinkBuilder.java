package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Link;
import org.uqbar.arena.graphics.Image;
import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ButtonBuilder;

import com.uqbar.commons.collections.Transformer;

//TODO: fix name "F"
public class JfaceLinkBuilder extends JFaceSkinnableControlBuilder<Link> implements ButtonBuilder {
	
	public JfaceLinkBuilder(JFaceContainer context) {
		super(context, new Link(context.getJFaceComposite(), SWT.BORDER));
	}

	public ButtonBuilder setCaption(String caption) {
		this.getWidget().setText(caption);
		return this;
	}

	@Override
	public ButtonBuilder setAsDefault() {
		return this;
	}

	@Override
	public void disableOnError() {
		new JFaceBindingBuilder(this, SWTObservables.observeEnabled(this.getWidget()), //
			new ComputedValue() {
				@Override
				protected Object calculate() {
					return ((IStatus) JfaceLinkBuilder.this.getContainer().getStatus().getValue()).isOK();
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
	
	@Override
	public BindingBuilder observeCaption() {
		return new JFaceBindingBuilder(this, new ButtonCaptionObservableValue(this.getWidget()));
	}
	
	@Override
	public <M> BindingBuilder observeImage(Transformer<M, Image> transformer) {
		throw new UnsupportedOperationException("Images are not supported on Link components!");
	}
	
}