package com.uqbar.apo

import scala.collection.mutable.Buffer

import org.apache.commons.lang.StringUtils

import javassist.CtBehavior
import javassist.CtConstructor
import javassist.CtMethod
import javassist.expr.ConstructorCall
import javassist.expr.FieldAccess
import javassist.expr.MethodCall

/**
 *
 * @author nny
 * @param <A>
 */
//TODO hacer que se pueda componer los interceptors
trait Interceptor[A] { self =>

  lazy val isEnabled = APOConfig.isEnable(this.propertyKey)
  val intercepts = Buffer[Behavior[_, _]]()
  var propertyKey = ""

  def before(fun: (A) => String) = { intercepts.append(Before(fun)); self }
  def after(fun: (A) => String) = { intercepts.append(After(fun)); self }
  def around(fun: (StringBuffer, A) => Unit) = { intercepts.append(Around(fun)); self }
  def read(fun: (StringBuffer, A) => Unit) = { intercepts.append(Read(fun)); self }
  def write(fun: (StringBuffer, A) => Unit) = { intercepts.append(Write(fun)); self }

  def intercept(statement: StringBuffer, a: A)

  /**
   * Utilizado para armar la key de habilitación en el framework-config.properties
   */

  def getType = List[java.lang.Class[A]]()

  implicit def replace(buffer: StringBuffer) = new StringBufferWriter(buffer)
}

trait FieldInterceptor extends Interceptor[FieldAccess] { self =>

  def intercept(statement: StringBuffer, field: FieldAccess) {
    var filter = Buffer[Behavior[FieldBehaviour, FieldAccess]]()
    if (field.isWriter()) {
      filter = intercepts.filter(_.isInstanceOf[Write[_, _]]).asInstanceOf[Buffer[Behavior[FieldBehaviour, FieldAccess]]]
    } else {
      filter = intercepts.filter(_.isInstanceOf[Read[_, _]]).asInstanceOf[Buffer[Behavior[FieldBehaviour, FieldAccess]]]
    }
    filter.foreach(call => call.proceed(statement, new FieldBehaviour(field)))
  }

  override def getType = super.getType.::(classOf[FieldAccess])
}

trait MethodInterceptor extends Interceptor[CtMethod] { self =>
  def intercept(statement: StringBuffer, method: CtMethod) {
    intercepts.asInstanceOf[Seq[Behavior[GenericBehaviour[CtMethod], CtMethod]]].foreach(call => call.proceed(statement, new GenericBehaviour(method)))
  }
  override def getType = super.getType.::(classOf[CtMethod])
}

trait ConstructorInterceptor extends Interceptor[CtConstructor] { self =>
  def intercept(statement: StringBuffer, method: CtConstructor) {
    intercepts.asInstanceOf[Seq[Behavior[GenericBehaviour[CtConstructor], CtConstructor]]].foreach(call => call.proceed(statement, new GenericBehaviour(method)))
  }
  override def getType = super.getType.::(classOf[CtConstructor])
}


trait MethodCallInterceptor extends Interceptor[MethodCall] { self =>
  def intercept(statement: StringBuffer, method: MethodCall) {
    intercepts.asInstanceOf[Seq[Behavior[MethodCallBehaviour, CtBehavior]]].foreach(call => call.proceed(statement, new MethodCallBehaviour(method)))
  }
  override def getType = super.getType.::(classOf[MethodCall])
}

trait ConstructorCallInterceptor extends Interceptor[ConstructorCall] { self =>
  def intercept(statement: StringBuffer, method: ConstructorCall) {
    intercepts.asInstanceOf[Seq[Behavior[ConstructorCallBehaviour, CtConstructor]]].foreach(call => call.proceed(statement, new ConstructorCallBehaviour(method)))
  }
  override def getType = super.getType.::(classOf[ConstructorCall])
}

class StringBufferWriter(buffer: StringBuffer) {
  def replace(reemplaze: String, newExpresion: String) {
    this.replace(StringUtils.replace(buffer.toString(), reemplaze, newExpresion));
  }

  def replace(reemplaze: String) {
    buffer.replace(0, buffer.length(), reemplaze);
  }
}


trait JavassisstWraperType[T]{
  def toBehaviour:CtBehavior
  def content:T
}

class GenericBehaviour[T<: CtBehavior](var content:T) extends JavassisstWraperType[T]{
  def toBehaviour = content
}

class FieldBehaviour(var content:FieldAccess) extends JavassisstWraperType[FieldAccess]{
  def toBehaviour = content.where()
}

class MethodCallBehaviour(var content:MethodCall) extends JavassisstWraperType[MethodCall]{
  def toBehaviour = content.where()
}

class ConstructorCallBehaviour(var content:ConstructorCall) extends JavassisstWraperType[ConstructorCall]{
  def toBehaviour = content.where()
}

