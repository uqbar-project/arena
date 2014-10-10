package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.lacar.ui.model.builder.traits.DisableEnable
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.swt.SWTObservables.{ observeVisible => observeVis, observeEditable, observeEnabled => observeEnab }
import org.uqbar.lacar.ui.impl.jface.builder.JFaceWidgetBuilder
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Text

/**
 * jface implementation of the trait disabled/enabled.
 */
trait JFaceEnabledDisabled extends DisableEnable {
  this: JFaceWidgetBuilder[_ <: Control] =>

  override def observeEnabled() = new JFaceBindingBuilder(this, 
      if (widget.isInstanceOf[Text]) observeEditable(widget) else observeEnab(widget))

}