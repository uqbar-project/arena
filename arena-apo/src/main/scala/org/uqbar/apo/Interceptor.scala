package org.uqbar.apo

import scala.collection.mutable.Buffer

import org.apache.commons.lang.StringUtils

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
  val intercepts = Buffer[Behavior[_]]()
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
    var filter = Buffer[Behavior[FieldAccess]]()
    if (field.isWriter()) {
      filter = intercepts.filter(_.isInstanceOf[Write[FieldAccess]]).asInstanceOf[Buffer[Behavior[FieldAccess]]]
    } else {
      filter = intercepts.filter(_.isInstanceOf[Read[FieldAccess]]).asInstanceOf[Buffer[Behavior[FieldAccess]]]
    }
    filter.foreach(call => call.proceed(statement, field))
  }

  override def getType = super.getType.::(classOf[FieldAccess])
}

trait MethodInterceptor extends Interceptor[CtMethod] { self =>
  def intercept(statement: StringBuffer, method: CtMethod) {
    intercepts.asInstanceOf[Seq[Behavior[CtMethod]]].foreach(call => call.proceed(statement, method))
  }
  override def getType = super.getType.::(classOf[CtMethod])
}

trait ConstructorInterceptor extends Interceptor[CtConstructor] { self =>
  def intercept(statement: StringBuffer, method: CtConstructor) {
    intercepts.asInstanceOf[Seq[Behavior[CtConstructor]]].foreach(call => call.proceed(statement, method))
  }
  override def getType = super.getType.::(classOf[CtConstructor])
}


trait MethodCallInterceptor extends Interceptor[MethodCall] { self =>
  def intercept(statement: StringBuffer, method: MethodCall) {
    intercepts.asInstanceOf[Seq[Behavior[CtMethod]]].foreach(call => call.proceed(statement, method.getMethod()))
  }
  override def getType = super.getType.::(classOf[MethodCall])
}

trait ConstructorCallInterceptor extends Interceptor[ConstructorCall] { self =>
  def intercept(statement: StringBuffer, method: ConstructorCall) {
    intercepts.asInstanceOf[Seq[Behavior[CtConstructor]]].foreach(call => call.proceed(statement, method.getConstructor()))
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