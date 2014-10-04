package org.uqbar.arena.scala

import org.mockito.internal.InternalMockHandler
import org.mockito.internal.configuration.ClassPathLoader
import org.mockito.internal.creation.settings.CreationSettings
import org.mockito.invocation.Invocation
import org.mockito.invocation.MockHandler
import org.uqbar.arena.widgets.TextBox
import org.uqbar.lacar.ui.model.Action
import com.uqbar.commons.collections.Transformer
import scala.reflect.ClassTag
import org.uqbar.arena.widgets.Control
import org.uqbar.arena.widgets.Control
import org.uqbar.arena.bindings.ObservableProperty
import scala.collection.mutable.ArrayBuffer
import org.uqbar.arena.bindings.typesafe.BindingMockHandler
import org.uqbar.arena.actions.AsyncActionDecorator
import org.uqbar.lacar.ui.model.bindings.Observable
import org.uqbar.lacar.ui.model.bindings.ViewObservable
import org.uqbar.lacar.ui.model.bindings.Binding
import org.uqbar.lacar.ui.model.ControlBuilder
import org.uqbar.lacar.ui.model.WidgetBuilder
import org.uqbar.lacar.ui.model.BindingBuilder
import org.uqbar.arena.widgets.Container
import org.uqbar.arena.widgets.Widget

/**
 * Contiene implicits utiles para usar arena desde scala
 * Asi evitar crear clases anonimas y poder usar bloques y funciones
 *
 * @author jfernandes
 */
object ArenaScalaImplicits {
  
  /** Convierte una funcion en un transformer   */
  implicit def closureToTransformer[I, O](closure: (I) => O): Transformer[I, O] = {
    new Transformer[I, O] {
      override def transform(i: I): O = {
        closure(i)
      }
    }
  }

  /** Convierte una funcion en un Action */
  implicit def closureToAction[I, O](closure: () => Unit) = new Action {
    override def execute() = closure()
  }
  
  /** Extension methods para Actions */
  implicit class ExtendedAction(var action:Action) {
    def async() = new AsyncActionDecorator(action)
    def +(other:Action) = new CompositeAction(action, other);
  }
  
  implicit def modelType(control: Control) = control.getContainerModelObject().getClass

  // *************************************
  // ** bindings
  // *************************************
  
  /**
   * Used to add bind as "extension methods"
   */
  implicit class Bindeable(control: Control) {
    
    // Este no deberia existir luego. Es algo especifico (value) 
    def bindValueTo[A: ClassTag, R](propertyBinder: A => R): Unit = {
      control.bindValue(propertyBinder)
    }
    
    def bind[A:ClassTag,R,C <: WidgetBuilder, V <:Widget](view:ViewObservable[V,_<:C], propertyBinder: A => R) : Binding[R,V,_<:C] = {
      control.addBinding(closureToObservable(propertyBinder), view)
    }
    
  }
  
  implicit class ObservableIdiomatic[A:ClassTag](var obj: A) {
    def @@[R](propertyBinder: A => R) = closureToObservable(propertyBinder)
  }

  implicit def closureToObservable[A:ClassTag, R](propertyBinder: A => R): ObservableProperty[R] = {
    // createMock
    val concreteModelType = implicitly[ClassTag[A]].runtimeClass.asInstanceOf[Class[A]]
    
    val handler = createInvocationHandler()
    val mock = createMockFor(concreteModelType, handler)

    // call closure
    propertyBinder.apply(mock)

    //TODO: inspect mock for getter calls, register binding.
    new ObservableProperty[R](handler buildPropertyExpression)
  }
  
  implicit class ViewObservableCombinators[V <: Control, C <: WidgetBuilder](var viewObservable: ViewObservable[V, C]) {
    def <=>[M](obj:Observable[M]) : Binding[M,V,C] = {
      viewObservable.bindTo(obj)
    }
  }
  
  def createInvocationHandler() = new BindingMockHandler

  def createMockFor[T](tipe: Class[T], handler: MockHandler): T = {
    ClassPathLoader.getMockMaker.createMock(createMockCreationSettings(tipe), handler)
  }

  def createMockCreationSettings[T](typeToMock: Class[T]) = {
    val settings = new CreationSettings[T]()
    settings setTypeToMock(typeToMock)
    settings
  }

}