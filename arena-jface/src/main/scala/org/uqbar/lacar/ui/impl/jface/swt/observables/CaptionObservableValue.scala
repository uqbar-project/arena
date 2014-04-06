package org.uqbar.lacar.ui.impl.jface.swt.observables

import org.eclipse.core.databinding.observable.Diffs
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue
import org.eclipse.swt.widgets.Control

/**
 * Defines new structural type to treat all swt components polymorphically.
 */
object WidgetWithText {
  type WithText = Control {
	  def getText():String
	  def setText(a:String)
  } 
}

/**
 * Observes a Control.text property (the caption).
 * It's useful to bind a model property to a button's caption.
 *
 * It's almost the same code as for a label's text.
 * It just also has a ".pack()" call after setting the text
 * Otherwise it was not refreshing the button.
 *
 * Introduces a type because SWT classes don't define a common interface for get/set Text.
 * 
 * @author jfernandes
 */
class CaptionObservableValue(component:WidgetWithText.WithText) extends AbstractSWTObservableValue(component) {

	override def doSetValue(value:Object) {
		val oldValue = component.getText
		val newValue = if (value == null)  "" else value.toString
		component.setText(newValue)
		component.pack
		
		if (!newValue.equals(oldValue)) {
			fireValueChange(Diffs.createValueDiff(oldValue, newValue))
		}
	}

	def doGetValue() = component.getText

	def getValueType() = classOf[String]

}
