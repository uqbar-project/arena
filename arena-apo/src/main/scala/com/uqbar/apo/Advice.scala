package com.uqbar.apo

import scala.collection.mutable.Buffer
import scala.reflect.Manifest
import com.uqbar.apo.parser.$originalAsigment
import com.uqbar.apo.parser.$originalReader
import com.uqbar.apo.parser.APOParser
import com.uqbar.apo.pointcut.PointCut
import javassist.expr.ExprEditor
import javassist.expr.FieldAccess
import javassist.CtMethod
import javassist.CtClass
import javassist.expr.ConstructorCall
import javassist.CtConstructor
import javassist.expr.MethodCall

/**
 * Intruduce bytecode para que se pueda interceptar todos los fields del objeto.
 *
 */

class Advice(var pointCut: PointCut, interceptor: Interceptor[_]*) extends ExprEditor {
  /**
   * Lista con FieldAccessInterceptors, para armar el codigo que se va a agregar a los accesors.
   */
  val interceptors = interceptor.toBuffer
  lazy val aopEnabled = APOConfig.isAOPEnable()

  // ***************************
  // ** FieldAccess weaving
  // ***************************

  override def edit(fieldAccess: FieldAccess) {
    var statement = new StringBuffer();
    if (fieldAccess.isWriter()) {
      statement.append($originalAsigment().name)
    }
    if (fieldAccess.isReader()) {
      statement.append($originalReader().name)
    }

    if (fieldAccess.where().getMethodInfo().toString().startsWith("<init>")) {
      print("")
    }

    val isEdit = edit[FieldAccess](fieldAccess, statement, classOf[FieldAccess])
    if (isEdit)
      fieldAccess.replace(APOParser.parse(fieldAccess, statement.toString()))
  }

  def edit(method: CtMethod) {
    var statement = new StringBuffer(method.getSignature())
    edit[CtMethod](method, statement, classOf[CtMethod])
  }

  override def edit(method: ConstructorCall) {
    var statement = new StringBuffer(method.getSignature())
    edit[CtConstructor](method.getConstructor(), statement, classOf[CtConstructor])
  }
  
  override def edit(method: MethodCall) {
    var statement = new StringBuffer(method.getSignature())
    edit[MethodCall](method, statement, classOf[MethodCall])
  }

  /**
   * @param fieldAccess
   * @param name Name of the field being processed
   */
  def edit[T: Manifest](expr: T, statement: StringBuffer, classType: Class[T]): Boolean = {
    var edit = false;
    if (aopEnabled) {
      try {
        val f = filterInterceptor[T]()
        f.foreach(interceptor => {
          if (pointCut.hasIntercept(expr)) {
            interceptor.intercept(statement, expr)
            edit = true;
          }
        })
      } catch {
        case e: Exception => throw this.getVerbosedException(e, statement.toString(), expr);
      }
    }
    edit
  }

  def apply(ctClass: CtClass) {
    ctClass.instrument(this);
    ctClass.getMethods().foreach(m => this.edit(m))
  }

  /**
   * Evita agregar el aspecto a los field access:
   * <ul>
   * <li>Estáticos.
   * <li>Que ocurran dentro de un constructor.
   * </ul>
   */
  def mustWeave(fieldAcces: FieldAccess): Boolean = {
    return !fieldAcces.isStatic() && this.aopEnabled;
  }

  // ***************************
  // ** Exception helpers
  // ***************************

  def filterInterceptor[T: Manifest](): Buffer[Interceptor[T]] = {
    this.interceptors.filter(_.getType.contains((manifest[T].erasure))).asInstanceOf[Buffer[Interceptor[T]]]
  }

  def getVerbosedException[T](ex: Exception, javaStatement: String, expr: T): RuntimeException = {
    return new APOException(javaStatement, ex);
  }

  def addInterceptor[A](interceptor: Interceptor[A]): Advice = {
    this.interceptors.append(interceptor);
    return this;
  }
}