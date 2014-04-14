package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.lacar.ui.model.builder.traits.DisableEnable
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.swt.SWTObservables
import org.uqbar.lacar.ui.impl.jface.builder.JFaceWidgetBuilder
import org.eclipse.swt.widgets.Control

/**
 * jface implementation of the trait disabled/enabled.
 */
trait JFaceEnabledDisabled extends DisableEnable {
  this : JFaceWidgetBuilder[_ <: Control] =>
  
  override def observeEnabled() = new JFaceBindingBuilder(this, SWTObservables.observeEnabled(widget))

}