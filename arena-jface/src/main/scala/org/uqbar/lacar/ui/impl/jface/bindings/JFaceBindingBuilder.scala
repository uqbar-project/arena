package org.uqbar.lacar.ui.impl.jface.bindings

import org.uqbar.lacar.ui.model.BindingBuilder
import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.uqbar.lacar.ui.impl.jface.builder.JFaceWidgetBuilder
import org.eclipse.core.databinding.conversion.IConverter
import org.eclipse.core.databinding.UpdateValueStrategy
import org.uqbar.arena.bindings.Transformer
import org.uqbar.ui.jface.base.BaseUpdateValueStrategy
import org.eclipse.core.runtime.IStatus
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.core.runtime.IStatus
import org.uqbar.arena.jface.JFaceImplicits.closureToComputedValue
import org.uqbar.lacar.ui.model.BindingBuilder

/**
 *
 * @author npasserini
 * @author jfernandes
 */
class JFaceBindingBuilder(val dbc: DataBindingContext, val container: JFaceContainer, var view: IObservableValue, var model: IObservableValue) 
	extends BindingBuilder {
  
  private var viewToModel = new BaseUpdateValueStrategy()
  private var modelToView = new BaseUpdateValueStrategy()

  def this(widget: JFaceWidgetBuilder[_], view: IObservableValue, model: IObservableValue) {
    this(widget.getDataBindingContext, widget.container , view, model)
  }
  
  def this(widget: JFaceWidgetBuilder[_], view: IObservableValue) {
	  this(widget, view, null);
  }

  override def observeProperty(model: Object, propertyName: String) {
    this.model = JFaceObservableFactory observeProperty(model, propertyName)
  }

  override def observeErrors() = {
    model = () => container.status.getValue.asInstanceOf[IStatus].isOK
  }

  protected def setConverter(viewToModel: UpdateValueStrategy, converter: IConverter) {
    viewToModel setConverter converter
  }

  override def adaptWith[M, V](transformer: Transformer[M, V]) = {
    setConverter(viewToModel, new IConverter() {
      override def getToType() = transformer.getModelType
      override def getFromType() = transformer.getViewType
      override def convert(value: Object) = transformer.viewToModel(value.asInstanceOf[V]).asInstanceOf[Object]
    })

    setConverter(modelToView, new IConverter() {
      override def getToType() = transformer getViewType
      override def getFromType() = transformer getModelType
      override def convert(value: Object) = transformer.modelToView(value.asInstanceOf[M]).asInstanceOf[Object]
    })
    this
  }
  
  override def viewToModel[M,V](transformer : com.uqbar.commons.collections.Transformer[V,M]) : this.type = {
    setConverter(viewToModel, new IConverter() {
      override def convert(value:Object) = transformer.transform(value.asInstanceOf[V]).asInstanceOf[Object];
      //TODO: is this really needed ?
      override def getToType() = classOf[Object]
      override def getFromType() = classOf[Object]
    });
    this
  }
  
  override def modelToView[M,V](transformer : com.uqbar.commons.collections.Transformer[M,V]) : this.type = {
    setConverter(modelToView, new IConverter() {
      override def convert(value:Object) = transformer.transform(value.asInstanceOf[M]).asInstanceOf[Object];
      //TODO: is this really needed ?
      override def getToType() = classOf[Object]
      override def getFromType() = classOf[Object]
    });
    this
  }

  override def build() = createBinding()

  /**
   * Creates the JFace data binding. Useful for adding behavior in subclasses that are aware of JFace.
   *
   * @return The JFace binding object
   */
  protected def createBinding() = dbc bindValue (view, model, viewToModel, modelToView)

}