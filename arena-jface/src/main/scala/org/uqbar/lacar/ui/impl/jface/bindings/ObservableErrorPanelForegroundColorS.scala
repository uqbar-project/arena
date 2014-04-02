package org.uqbar.lacar.ui.impl.jface.bindings

import org.eclipse.core.databinding.observable.value.ComputedValue
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.AggregateValidationStatus
import org.eclipse.core.runtime.IStatus
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color

/**
 * 
 * @author jfernandes
 */
class ObservableErrorPanelForegroundColorS(val status : AggregateValidationStatus)  extends ComputedValue {

  override def calculate() : Color = {
		return status.getValue().asInstanceOf[IStatus];
  }
  
  implicit def istatus_to_color(status : IStatus) : Color = 
    if (status.isOK()) 
      SWT.COLOR_BLACK.asInstanceOf[Integer] 
    else 
      SWT.COLOR_RED.asInstanceOf[Integer]

  implicit def swtintcolor_to_color(colorCode : Integer) : Color = Display.getCurrent().getSystemColor(colorCode)

}