package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.SWT
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.events.VerifyEvent
import org.eclipse.swt.widgets.Text
import org.uqbar.arena.jface.JFaceImplicits.closureToModifyListener
import org.uqbar.arena.jface.JFaceImplicits.closureToVerifyListener
import org.uqbar.arena.jface.JFaceImplicits.verifyEventToTextInputEvent
import org.uqbar.arena.widgets.TextFilter
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.model.TextControlBuilder

class JFaceTextBuilder(container:JFaceContainer, multiLine:Boolean) 
	extends JFaceSkinnableControlBuilder[Text](container, new Text(container.getJFaceComposite(), if (multiLine) SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP else SWT.SINGLE | SWT.BORDER)) 
	with TextControlBuilder {
  
	override def observeValue() = new JFaceBindingBuilder(this, SWTObservables.observeText(widget, SWT.Modify))
	
	override def selectFinalLine() = {
	  widget addModifyListener((e:ModifyEvent) => widget setSelection(widget.getText.length))
	}

	override def addTextFilter(filter:TextFilter) = {
	  widget addVerifyListener((event:VerifyEvent) => event.doit = filter accept(event))
	}
  
}