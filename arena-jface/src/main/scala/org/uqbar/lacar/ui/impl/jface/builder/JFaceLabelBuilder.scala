package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.jface.databinding.swt.SWTObservables.observeText
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Label
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.JFaceSkinnableControlBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.model.LabelBuilder

class JFaceLabelBuilder(container:JFaceContainer, label: Label) 
	extends JFaceSkinnableControlBuilder[Label](container, label)
	with LabelBuilder 
	with WithImageControlBuilder[Label] {
  
  def this(container: JFaceContainer) = this(container, new Label(container.getJFaceComposite, SWT.CENTER))

  override def setText(text:String) = { getWidget.setText(text) }
  
  override def observeValue() = new JFaceBindingBuilder(this, observeText(getWidget))
  
}