package org.uqbar.lacar.ui.impl.jface.bindings;
import java.awt.Color;
import java.util.List;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue;
import org.eclipse.jface.internal.databinding.swt.SWTProperties;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Control;

import scala.actors.threadpool.Arrays;

import com.uqbar.commons.collections.Transformer;

/**
 * Inspired by: org.eclipse.jface.internal.databinding.swt.ControlObservableValue
 *
 */
public class ControlObservableValue<T, U> extends AbstractSWTObservableValue {

	private final Control control;
	private final String attribute;
	
	@SuppressWarnings("unchecked")
	private static final List<String> SUPPORTED_ATTRIBUTES = Arrays.asList( new String[]{
				SWTProperties.FOREGROUND, SWTProperties.BACKGROUND, SWTProperties.FONT});
	private final Transformer<T, U> transformer;
	
	/**
	 * @param control
	 * @param attribute
	 */
	public ControlObservableValue(Control control, String attribute, Transformer<T, U> transformer) {
		super(control);
		this.control = control;
		this.attribute = attribute;
		this.transformer = transformer;
		if (!SUPPORTED_ATTRIBUTES.contains(attribute)) {
			throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("unchecked")
	public void doSetValue(Object modelValue) {
		Object oldValue = doGetValue();
		Object value = transformer.transform((T) modelValue);
		if(value== null)
			return;
		if (attribute.equals(SWTProperties.FOREGROUND)) {
			control.setForeground(getSWTColor((Color) value));
		} else if (attribute.equals(SWTProperties.BACKGROUND)) {
			control.setBackground(getSWTColor((Color) value));
		} else if (attribute.equals(SWTProperties.FONT)) {
			control.setFont((Font) value);
		}
		fireValueChange(Diffs.createValueDiff(oldValue, value));
	}

	public Object doGetValue() {
		return null;
	}

	public Object getValueType() {
		return Object.class;
	}
	protected org.eclipse.swt.graphics.Color getSWTColor(Color color) {
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(getWidget().getDisplay(), red, green, blue);
		return swtColor;
	}
}