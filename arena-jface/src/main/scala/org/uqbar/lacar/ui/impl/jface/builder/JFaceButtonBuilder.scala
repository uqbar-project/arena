package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.core.runtime.IStatus
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Button
import org.uqbar.arena.jface.JFaceImplicits.closureToComputedValue
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.JFaceSkinnableControlBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceClickable
import org.uqbar.lacar.ui.impl.jface.swt.observables.CaptionObservableValue
import org.uqbar.lacar.ui.model.BindingBuilder
import org.uqbar.lacar.ui.model.ButtonBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceWithCaption
import org.uqbar.lacar.ui.impl.jface.builder.traits.WithImageControlBuilder

/**
 * @author jfernandes
 */
class JFaceButtonBuilder(c: JFaceContainer)
  extends JFaceSkinnableControlBuilder[Button](c, new Button(c getJFaceComposite, SWT PUSH))
  with ButtonBuilder
  with WithImageControlBuilder[Button]
  with JFaceClickable
  with JFaceWithCaption
{

  override def setAsDefault() = {
    getWidget.getShell.setDefaultButton(getWidget)
    this
  }

  override def disableOnError() = {
    new JFaceBindingBuilder(this, observeEnabled(getWidget), () => computeValue()).build
  }

  def computeValue(): Object = getContainer.getStatus.getValue.asInstanceOf[IStatus].isOK.asInstanceOf[Object]

  override def observeValue() : BindingBuilder = throw new UnsupportedOperationException("Se intentó observar la propiedad 'value' de un Button, que no tiene dicha propiedad")

}