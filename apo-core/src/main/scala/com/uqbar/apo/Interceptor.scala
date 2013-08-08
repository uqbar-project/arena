package com.uqbar.apo

import scala.collection.mutable.Buffer
import org.apache.commons.lang.StringUtils
import javassist.expr.FieldAccess
import javassist.CtMethod

/**
 * 
 * @author nny
 * @param <A>
 */
//TODO hacer que se pueda componer los interceptors
trait Interceptor[A] {self =>

  lazy val isEnabled = APOConfig.isEnable(this.propertyKey)
  val intercepts = Buffer[Behavior[_]]()
  var propertyKey = ""

  def before(fun: (A) => String) = {intercepts.append(Before(fun));self}
  def after(fun: (A) => String) = {intercepts.append(After(fun));self}
  def around(fun: (StringBuffer, A) => Unit) = {intercepts.append(Around(fun));self}

  def intercept(statement: StringBuffer, a: A)

  /**
   * Utilizado para armar la key de habilitación en el framework-config.properties
   */

  def getType: java.lang.Class[A]
  
  implicit def replace(buffer:StringBuffer)= new StringBufferWriter(buffer)
}



trait FieldInterceptor extends Interceptor[FieldAccess] { self =>

  def read(fun: (StringBuffer, FieldAccess) => Unit) = {intercepts.append(ReadField(fun));self}
  def write(fun: (StringBuffer, FieldAccess) => Unit) = {intercepts.append(WriteField(fun));self}

  def intercept(statement: StringBuffer, field: FieldAccess) {
    var filter = Buffer[Behavior[FieldAccess]]()
    if (field.isWriter()) {
      filter = intercepts.filter(_.isInstanceOf[WriteField]).asInstanceOf[Buffer[Behavior[FieldAccess]]]
    } else {
      filter = intercepts.filter(_.isInstanceOf[ReadField]).asInstanceOf[Buffer[Behavior[FieldAccess]]]
    }
    filter.foreach(call => call.proceed(statement, field))
  }

  def getType = classOf[FieldAccess]
}



trait MethodInterceptor extends Interceptor[CtMethod] {

  def intercept(statement: StringBuffer, method: CtMethod) {
    intercepts.asInstanceOf[Seq[Behavior[CtMethod]]].foreach(call => call.proceed(statement, method))
  }

  def getType  = classOf[CtMethod]

}



class StringBufferWriter(buffer:StringBuffer){
  def replace(reemplaze:String, newExpresion:String){
    this.replace(StringUtils.replace(buffer.toString(), reemplaze, newExpresion));
  }
  
  def replace(reemplaze:String){
    buffer.replace(0, buffer.length(), reemplaze);
  }
}