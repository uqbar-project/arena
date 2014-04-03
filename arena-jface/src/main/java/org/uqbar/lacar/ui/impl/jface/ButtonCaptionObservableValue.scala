package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.observable.Diffs
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Widget
import org.eclipse.swt.widgets.Control

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
class ButtonCaptionObservableValue(component:Control) extends AbstractSWTObservableValue(component) {

	override def doSetValue(value:Object) {
		val oldValue = component.asInstanceOf[{def getText():String}].getText()
		val newValue = if(value == null)  "" else value.toString()
		component.asInstanceOf[{def setText(a:String)}].setText(newValue);
		component.pack();
		
		if (!newValue.equals(oldValue)) {
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));
		}
	}

	def doGetValue() = component.asInstanceOf[{def getText():String}].getText()

	def getValueType() = classOf[String]

}
