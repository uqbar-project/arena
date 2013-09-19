package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue;
import org.eclipse.swt.widgets.Button;

/**
 * Observes a Button.text property (the caption).
 * It's useful to bind a model property to a button's caption.
 *
 * It's almost the same code as for a label's text.
 * It just also has a ".pack()" call after setting the text
 * Otherwise it was not refreshing the button.
 *
 * TODO: introduce a generic ReflectiveObservableValue("text", button)
 *   To avoid having a subclass for every control property.
 * 
 * 
 * @author jfernandes
 */
public class ButtonCaptionObservableValue extends AbstractSWTObservableValue {
	private final Button button;

	/**
	 * @param label
	 */
	public ButtonCaptionObservableValue(Button button) {
		super(button);
		this.button = button;
	}
	
	/**
	 * @param realm
	 * @param label
	 */
	public ButtonCaptionObservableValue(Realm realm, Button button) {
		super(realm, button);
		this.button = button;
	}

	public void doSetValue(final Object value) {
		String oldValue = this.button.getText();
		String newValue = value == null ? "" : value.toString(); //$NON-NLS-1$
		this.button.setText(newValue);
		this.button.pack();
		
		if (!newValue.equals(oldValue)) {
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));
		}
	}

	public Object doGetValue() {
		return this.button.getText();
	}

	public Object getValueType() {
		return String.class;
	}

}
