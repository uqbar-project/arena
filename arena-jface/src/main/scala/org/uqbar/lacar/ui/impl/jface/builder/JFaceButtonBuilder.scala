package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.core.runtime.IStatus
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Button
import org.uqbar.arena.jface.JFaceImplicits.closureToComputedValue
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceClickable
import org.uqbar.lacar.ui.model.BindingBuilder
import org.uqbar.lacar.ui.model.ButtonBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceWithCaption
import org.uqbar.lacar.ui.impl.jface.builder.traits.WithImageBuilder
import org.eclipse.jface.databinding.swt.SWTObservables
import org.uqbar.lacar.ui.impl.jface.builder.traits.Aesthetic
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceSizeable
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceEnabledDisabled

/**
 * @author jfernandes
 */
class JFaceButtonBuilder(c: JFaceContainer)
  extends JFaceSkinnableControlBuilder[Button](c, new Button(c getJFaceComposite, SWT PUSH))
//	extends JFaceWidgetBuilder[Button](c, new Button(c getJFaceComposite, SWT PUSH))
  with ButtonBuilder
  with WithImageBuilder[Button]
  with JFaceClickable
  with JFaceWithCaption
  with Aesthetic
  with JFaceSizeable
  with JFaceEnabledDisabled
{

  override def setAsDefault() = {
    widget.getShell.setDefaultButton(widget)
    this
  }

  override def disableOnError() = {
    new JFaceBindingBuilder(this, SWTObservables.observeEnabled(widget), computeValue _).build
  }

  def computeValue(): Object = container.status.getValue.asInstanceOf[IStatus].isOK.asInstanceOf[Object]

  override def observeValue() : BindingBuilder = throw new UnsupportedOperationException("Se intentó observar la propiedad 'value' de un Button, que no tiene dicha propiedad")

}