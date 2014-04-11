package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.swt.widgets.Spinner
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.eclipse.swt.SWT
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.swt.SWTObservables
import org.uqbar.lacar.ui.impl.jface.builder.traits.SelectionAsValue

object JFaceSpinnerBuilder {
  
  def createSpinner(container:JFaceContainer, minValue:Integer, maxValue:Integer) : Spinner = {
		val spinner = new Spinner(container getJFaceComposite, SWT.SINGLE | SWT.BORDER)
		if (maxValue != null) {
			spinner setMaximum maxValue
		}
		if (minValue != null) {
			spinner setMinimum minValue
		}
		spinner
	}
  
}

/**
 * Control builder for the {@link Spinner} control.
 * It changes the way it observers the value due to the way it needs to be done in swt.
 * 
 * @author jfernandes
 */
class JFaceSpinnerBuilder(container:JFaceContainer, minValue:Integer, maxValue:Integer) 
	extends JFaceControlBuilder[Spinner](container, JFaceSpinnerBuilder.createSpinner(container, minValue, maxValue))
    with SelectionAsValue {

}