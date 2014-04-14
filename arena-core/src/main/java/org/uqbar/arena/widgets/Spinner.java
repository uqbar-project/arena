package org.uqbar.arena.widgets;

import java.lang.reflect.Method;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

import org.uqbar.commons.model.Model;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

/**
 * Arena's control that allows you to edit a numeric value through a textbox which has two buttons
 * with it in order to increment / decrement the current value.
 * 
 * Also supports having a maximum and minimum value which will cause the corresponding buttons to get
 * enabled/disabled when reached the specified value.
 * 
 * The control also allows the user to increment/decrement the value throw the up, and down-arrows of the keyboard.
 * Also you can type the number directly. 
 * 
 * @author jfernandes
 */
public class Spinner extends Control {
	private Integer maximumValue;
	private Integer minimumValue;

	public Spinner(Container container) {
		super(container);
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		return container.addSpinner(this.getMinimumValue(), this.getMaximumValue());
	}
	
	@Override
	public <C extends ControlBuilder> Binding<C> bindValueToProperty(String modelProperty) {
		Method getter = Model.getPropertyDescriptor(this.getContainer().getModel(), modelProperty).getReadMethod();
		if (getter.isAnnotationPresent(Min.class)) {
			this.setMinimumValue((int) getter.getAnnotation(Min.class).value());
		}
		if (getter.isAnnotationPresent(Max.class)) {
			this.setMaximumValue((int) getter.getAnnotation(Max.class).value());
		}
		return super.bindValueToProperty(modelProperty);
	}

	public void setMaximumValue(Integer maximumValue) {
		this.maximumValue = maximumValue;
	}
	
	public Integer getMaximumValue() {
		return this.maximumValue;
	}
	
	public Integer getMinimumValue() {
		return this.minimumValue;
	}
	
	public void setMinimumValue(Integer minimumValue) {
		this.minimumValue = minimumValue;
	}

}
