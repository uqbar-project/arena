package org.uqbar.lacar.ui.impl.jface.swt.observables;
import java.awt.Color;
import java.util.List;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Control;

import scala.actors.threadpool.Arrays;

/**
 * Inspired by: org.eclipse.jface.internal.databinding.swt.ControlObservableValue
 */
// esta clase podria no tener demasiado sentido luego de que le saqué el transformer.
// la unica diferencia con la de jface es que trabajar con Colors de awt en lugar de swt.
// si esa conversion se hace un paso previo a este objeto, podriamos usar el de jface y listo.
//public class ControlObservableValue extends AbstractSWTObservableValue {
//	private final Control control;
//	private final String attribute;
//	
//	@SuppressWarnings("unchecked")
//	private static final List<String> SUPPORTED_ATTRIBUTES = Arrays.asList( new String[]{
//				SWTProperties.FOREGROUND, SWTProperties.BACKGROUND, SWTProperties.FONT});
//	
//	/**
//	 * @param control
//	 * @param attribute
//	 */
//	public ControlObservableValue(Control control, String attribute) {
//		super(control);
//		this.control = control;
//		this.attribute = attribute;
//		if (!SUPPORTED_ATTRIBUTES.contains(attribute)) {
//			throw new IllegalArgumentException();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public void doSetValue(Object modelValue) {
//		Object oldValue = doGetValue();
//		if(modelValue== null)
//			return;
//		if (attribute.equals(SWTProperties.FOREGROUND)) {
//			control.setForeground(getSWTColor((Color) modelValue));
//		} else if (attribute.equals(SWTProperties.BACKGROUND)) {
//			control.setBackground(getSWTColor((Color) modelValue));
//		} else if (attribute.equals(SWTProperties.FONT)) {
//			control.setFont((Font) modelValue);
//		}
//		fireValueChange(Diffs.createValueDiff(oldValue, modelValue));
//	}
//
//	public Object doGetValue() {
//		return null;
//	}
//
//	public Object getValueType() {
//		return Object.class;
//	}
//	protected org.eclipse.swt.graphics.Color getSWTColor(Color color) {
//		return new org.eclipse.swt.graphics.Color(getWidget().getDisplay(), color.getRed(), color.getGreen(), color.getBlue());
//	}
//}