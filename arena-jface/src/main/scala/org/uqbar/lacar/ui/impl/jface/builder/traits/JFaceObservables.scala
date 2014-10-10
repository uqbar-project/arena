package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.swt.SWTObservables
import org.uqbar.lacar.ui.impl.jface.builder.JFaceWidgetBuilder
import org.eclipse.swt.widgets.Control

trait SelectionAsValue {
  this: JFaceWidgetBuilder[_ <: Control] =>

  def observeValue() = new JFaceBindingBuilder(this, SWTObservables.observeSelection(widget))
}
