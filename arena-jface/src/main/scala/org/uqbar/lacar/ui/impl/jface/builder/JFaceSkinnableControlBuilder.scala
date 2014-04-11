package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.lacar.ui.model.builder.traits.SkinnableBuilder
import org.eclipse.swt.widgets.Control
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.builder.traits.Aesthetic
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.swt.SWTObservables

//no tiene mucho sentido esta clase ahora que Aesthetic es un trait.
abstract class JFaceSkinnableControlBuilder[T <: Control](container:JFaceContainer)
		extends JFaceControlBuilder[T](container) 
		with SkinnableBuilder
		with Aesthetic {

  def this(container:JFaceContainer, jfaceWidget:T) {
		this(container)
		this.initialize(jfaceWidget)
  }
  
  def getControl() = widget
  
  def observeBackground() = new JFaceBindingBuilder(this, SWTObservables.observeBackground(widget))
		
}