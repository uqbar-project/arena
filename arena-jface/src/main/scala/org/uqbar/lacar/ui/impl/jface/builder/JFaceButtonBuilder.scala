package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Button
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.JFaceSkinnableControlBuilder
import org.uqbar.lacar.ui.model.ButtonBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.swt.SWTObservables._
import org.eclipse.core.databinding.observable.value.ComputedValue
import org.eclipse.core.runtime.IStatus
import org.uqbar.arena.jface.JFaceImplicits._
import org.uqbar.lacar.ui.model.Action
import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter
import org.uqbar.lacar.ui.impl.jface.swt.observables.ButtonCaptionObservableValue
import org.uqbar.lacar.ui.model.BindingBuilder

class JFaceButtonBuilder(c: JFaceContainer)
  extends JFaceSkinnableControlBuilder[Button](c, new Button(c.getJFaceComposite(), SWT.PUSH))
  with ButtonBuilder
  with WithImageControlBuilder[Button] {

  def setCaption(caption: String) = {
    getWidget setText caption
    this
  }

  override def setAsDefault() = {
    getWidget.getShell.setDefaultButton(getWidget)
    this
  }

  override def disableOnError() = {
    new JFaceBindingBuilder(this, observeEnabled(getWidget), () => computeValue()).build
  }

  def computeValue(): Object = getContainer.getStatus.getValue.asInstanceOf[IStatus].isOK.asInstanceOf[Object]

  override def onClick(action: Action) = {
    getWidget.addSelectionListener(new JFaceActionAdapter(getContainer, action))
    this
  }

  override def observeValue() : BindingBuilder = throw new UnsupportedOperationException("Se intentó observar la propiedad 'value' de un Button, que no tiene dicha propiedad")

  override def observeCaption() = new JFaceBindingBuilder(this, new ButtonCaptionObservableValue(this.getWidget()));

}