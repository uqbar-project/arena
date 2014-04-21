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

  implicit def closureToAction[I, O](closure: () => Unit) = new Action {
    override def execute() = closure()
  }
  
  implicit def modelType(control: Control) = control.getContainerModelObject().getClass

  /**
   * Used to add bindValue as "extension methods"
   */
  implicit class Bindeable(control: Control) {
    def bindValueTo[A: ClassTag, R](propertyBinder: A => R): Unit = {
      control.bindValue(propertyBinder)
    }
  }
  
  implicit class ExtendedAction(var action:Action) {
    def async() = new AsyncActionDecorator(action)
  }

  implicit def closureToObservable[A: ClassTag, R](propertyBinder: A => R): ObservableProperty = {
    // createMock
    val concreteModelType = implicitly[ClassTag[A]].runtimeClass.asInstanceOf[Class[A]]
    val handler = createInvocationHandler()
    val mock: A = createMockFor(concreteModelType, handler)

    // call closure
    propertyBinder.apply(mock)

    //TODO: inspect mock for getter calls, register binding.
    new ObservableProperty(handler buildPropertyExpression)
  }

  def createInvocationHandler() = new BindingMockHandler

  def createMockFor[T](tipe: Class[T], handler: MockHandler): T = {
    ClassPathLoader.getMockMaker.createMock(createMockCreationSettings(tipe), handler)
  }

  def createMockCreationSettings[T](typeToMock: Class[T]) = {
    val settings = new CreationSettings[T]()
    settings.setTypeToMock(typeToMock)
    settings
  }

}