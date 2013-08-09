package org.uqbar.lacar.ui.impl.jface;

import java.util.concurrent.Callable;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;
import org.uqbar.arena.widgets.TextFilter;
import org.uqbar.arena.widgets.TextInputEvent;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.TextControlBuilder;

/**
 * Construye un campo de texto simple.
 * 
 * @author npasserini
 */
public class JFaceTextBuilder extends JFaceSkinnableControlBuilder<Text> implements TextControlBuilder {

	public JFaceTextBuilder(JFaceContainer container) {
		super(container, new Text(container.getJFaceComposite(), SWT.SINGLE | SWT.BORDER));
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeText(this.getWidget(), SWT.Modify));
	}

	@Override
	public void addTextFilter(final TextFilter filter) {
		this.getWidget().addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent event) {
				TextInputEvent adaptedEvent = new TextInputEvent(event.start, event.end, event.text,
					new Callable<String>() {
						@Override
						public String call() throws Exception {
							return ((Text) event.widget).getText();
						}
					});
				event.doit = filter.accept(adaptedEvent);
			}
		});
		/*
		 * this.getWidget().addVerifyListener(new VerifyListener() {
		 * 
		 * @Override public void verifyText(VerifyEvent event) { System.out.println("event: " + event.text +
		 * " start:" + event.start + " end:" + event.end + " widgetText:" + ((Text) event.widget).getText());
		 * } });
		 */
	}

}
