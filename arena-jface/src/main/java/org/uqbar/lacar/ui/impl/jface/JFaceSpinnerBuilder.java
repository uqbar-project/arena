package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;

/**
 * Control builder for the {@link Spinner} control.
 * It changes the way it observers the value due to the way it needs to be done in swt.
 * 
 * @author jfernandes
 */
public class JFaceSpinnerBuilder extends JFaceControlBuilder<Spinner> {

	public JFaceSpinnerBuilder(JFaceContainer container, Integer minValue, Integer maxValue) {
		super(container, createSpinner(container, minValue, maxValue));
	}

	protected static Spinner createSpinner(JFaceContainer container, Integer minValue, Integer maxValue) {
		Spinner spinner = new Spinner(container.getJFaceComposite(), SWT.SINGLE | SWT.BORDER);
		if (maxValue != null) {
			spinner.setMaximum(maxValue);
		}
		if (minValue != null) {
			spinner.setMinimum(minValue);
		}
		return spinner;
	}
	
	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeSelection(this.getWidget()));
	}

}
