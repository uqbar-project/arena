package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.arena.jface.JFaceImplicits.closureToModifyListener
import org.uqbar.arena.jface.JFaceImplicits.closureToVerifyListener
import org.uqbar.arena.jface.JFaceImplicits.verifyEventToTextInputEvent
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.model.TextControlBuilder
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Text
import org.uqbar.arena.widgets.traits.WidgetWithAlignment


class JFaceTextBuilder(container:JFaceContainer, multiLine:Boolean, numeric: Boolean = false) 
	extends AbstractJFaceTextBuilder(container, new Text(container.getJFaceComposite(), 
	    (if (multiLine) SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP else SWT.SINGLE | SWT.BORDER)
	    | (if (numeric) SWT.RIGHT else SWT.LEFT))) 
	with TextControlBuilder 
	with WidgetWithAlignment {

	override def alignLeft() = text.setOrientation(SWT.LEFT_TO_RIGHT)
  override def alignRight() = text.setOrientation(SWT.RIGHT_TO_LEFT)
  override def alignCenter() = text.setOrientation(SWT.LEFT_TO_RIGHT) // you can't align text center
  
}
