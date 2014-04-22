package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.lacar.ui.model.ControlBuilder
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.SWT
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.swt.observables.ControlObservableValue
import org.eclipse.jface.internal.databinding.swt.SWTProperties
import org.eclipse.swt.widgets.Text
import org.eclipse.jface.databinding.swt.SWTObservables.{ observeVisible => observeVis, observeEditable, observeEnabled => observeEnab }
import com.uqbar.commons.collections.Transformer
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.layout.RowData
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceSizeable
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceEnabledDisabled

/**
 * @author npasserini
 * @author jfernandes
 */
abstract class JFaceControlBuilder[T <: Control](c: JFaceContainer)
  extends JFaceWidgetBuilder[T](c)
  with ControlBuilder
  with JFaceSizeable
  with JFaceEnabledDisabled {

  def this(container: JFaceContainer, jfaceWidget: T) {
    this(container)
    this.initialize(jfaceWidget)
  }
  
  override def observeBackground() = new JFaceBindingBuilder(this, new ControlObservableValue(widget, SWTProperties.BACKGROUND))
  override def observeVisible() = new JFaceBindingBuilder(this, observeVis(widget))

  def observeEnabled(t: T) = if (t.isInstanceOf[Text]) observeEditable(t) else observeEnab(t)

  /**
   * Utilizado para simplificar la construcción bindings de bajo nivel en forma manual.
   *
   * ATENCIÓN: Esto debe usarse sólo en casos que realmente ameriten la programación a bajo nivel, la forma
   * preferida de agregar un binding es utilizando los métodos <code>#observeXXX</code> que se encuentran en
   * las interfaces que dependen de {@link WidgetBuilder} y que devuelven un {@link BindingBuilder} que
   * permite configurar un binding sin necesidad de escribir código dependiente de la tecnología.
   *
   * @param model
   * @param view
   */
  def bind(model: IObservableValue, view: IObservableValue) = new JFaceBindingBuilder(this, view, model).build

  def getControlLayout(): Control = widget

}