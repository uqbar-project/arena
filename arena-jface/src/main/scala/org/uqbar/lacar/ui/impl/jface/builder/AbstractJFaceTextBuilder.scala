package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.SWT
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.events.VerifyEvent
import org.eclipse.swt.widgets.Text
import org.uqbar.arena.filters.TextFilter
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.model.TextControlBuilder
import org.uqbar.arena.jface.JFaceImplicits.closureToModifyListener
import org.uqbar.arena.jface.JFaceImplicits.closureToVerifyListener
import org.uqbar.arena.jface.JFaceImplicits.verifyEventToTextInputEvent

class AbstractJFaceTextBuilder(container: JFaceContainer)
	extends JFaceSkinnableControlBuilder[Text](container)
	with TextControlBuilder {

  var text : Text = null
  
  def this(container:JFaceContainer, jfaceWidget:Text) {
		this(container)
		text = jfaceWidget
		this.initialize(jfaceWidget)
  }
  
	override def observeValue() = new JFaceBindingBuilder(this, SWTObservables.observeText(widget, SWT.Modify))
	
	override def selectFinalLine() = {
	  widget addModifyListener((e:ModifyEvent) => widget setSelection(widget.getText.length))
	}

	override def addTextFilter(filter:TextFilter) = {
	  widget addVerifyListener((event:VerifyEvent) => event.doit = filter accept(event))
	}
  
}