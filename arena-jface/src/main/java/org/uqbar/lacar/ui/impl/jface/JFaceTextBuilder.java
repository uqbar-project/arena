package org.uqbar.lacar.ui.impl.jface;

import java.util.concurrent.Callable;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

	public JFaceTextBuilder(JFaceContainer container, boolean multiLine) {
		super(container, new Text(container.getJFaceComposite(), multiLine ? SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP: SWT.SINGLE | SWT.BORDER));
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeText(this.getWidget(), SWT.Modify));
	}
	
	@Override
	public void selectFinalLine(){
		getWidget().addModifyListener(new ModifyListener() {
	        @Override
	        public void modifyText(ModifyEvent e) {
	        	getWidget().setSelection(getWidget().getText().length());
	        }
	    });
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
