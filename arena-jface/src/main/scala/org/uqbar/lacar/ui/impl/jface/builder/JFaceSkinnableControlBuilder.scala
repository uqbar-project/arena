package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.swt.widgets.Control
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.builder.traits.Aesthetic
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.swt.SWTObservables
import org.uqbar.lacar.ui.model.builder.traits.StyledWidgetBuilder
import org.uqbar.lacar.ui.model.builder.StyledControlBuilder

//no tiene mucho sentido esta clase ahora que Aesthetic es un trait.
abstract class JFaceSkinnableControlBuilder[T <: Control](container:JFaceContainer)
		extends JFaceControlBuilder[T](container)
		with StyledControlBuilder
		with Aesthetic {

  def this(container:JFaceContainer, jfaceWidget:T) {
		this(container)
		this.initialize(jfaceWidget)
  }
  
  def getControl() = widget
  
  def observeBackground() = new JFaceBindingBuilder(this, SWTObservables.observeBackground(widget))
		
}