package com.uqbar.apo.pointcut

import javassist.CtField
import javassist.CtClass
import javassist.expr.FieldAccess
import javassist.expr.MethodCall
import scala.collection.mutable.Buffer
import javassist.ClassPool
import javassist.CtMethod
import javassist.Modifier
import com.uqbar.apo.parser.APOParser
import javassist.bytecode.Descriptor
import javassist.expr.ConstructorCall

abstract class PointCut {
  def evaluate(ctClass: CtClass): Boolean
  def hasIntercept(any: Any): Boolean
}

case class F[T](fun: (T) => Boolean, op: {def &&(fun: F[T]): F[T]
										  def ||(fun: F[T]): F[T]
										}) extends Function1[T, Boolean] {
  def apply(t: T) = fun(t)
  def &&(fun: F[T]) = op && (fun)
  def ||(fun: F[T]) = op || (fun)
}

case class FClass(fun: (CtClass) => Boolean, op: {def &&(fun: FClass): FClass
										  def ||(fun: FClass): FClass
										}) extends Function1[CtClass, Boolean] {
  def apply(t: CtClass) = fun(t)
  def &&(fun: FClass) = op && (fun)
  def ||(fun: FClass) = op || (fun)
}


trait InterceptMatchPointCut[T] extends PointCut{
  var pointcuts: F[T] = _

  override def hasIntercept(any: Any): Boolean = {
    any match {
      case t: T => if(pointcuts == null) true else pointcuts(t)
      case _ => false;
    }
  }

  def &&(func: F[T]):F[T] = { val p = pointcuts ;pointcuts = F((t: T) => { p(t) && func(t) }, this); pointcuts }
  def ||(func: F[T]):F[T] = { val p = pointcuts; pointcuts = F((t: T) => { p(t) || func(t) }, this); pointcuts }

  def filter(fun: (T) => Boolean) = if(pointcuts == null) {pointcuts = F(fun, this); pointcuts} else F(fun, this)  
}

trait FieldPointCut extends InterceptMatchPointCut[FieldAccess] {
  filter((field) => { !field.isStatic() && !field.where().getMethodInfo().toString().startsWith("$lessinit")})

  def fieldName(fun: (String) => Boolean) = filter(field => fun(field.getFieldName()))
  def field(fun: (FieldAccess) => Boolean) = filter(field => fun(field))
  def fieldType[T: Manifest] = filter(_.getField().getType().getName() == manifest[T].erasure.getName())
  def constructor() = filter(field=> { field.where().getMethodInfo().toString().startsWith("<init>") })
}

trait MethodPointCut extends InterceptMatchPointCut[CtMethod] {
  def methodName(fun: (String) => Boolean) = filter(method => fun(method.getName()))
  def method(fun: (CtMethod) => Boolean) = filter(fun)
  def modifiers(modifiers: Int) = filter(_.getModifiers() == modifiers)
  def arguments(arguments: Class[_]*) = filter(method => {
    Descriptor.toString(method.getSignature()).startsWith(arguments.map(_.getName).mkString("(", ",", ")"))
  })

  def constructor() = filter(method => { method.getMethodInfo().toString().startsWith("<init>") })

}

trait MethodCallPointCut extends InterceptMatchPointCut[MethodCall] {
}

trait ConstructorCallPointCut extends InterceptMatchPointCut[ConstructorCall] {
}


trait MatchPointCut extends PointCut {
  var filters: FClass = _

  def &&(func: FClass):FClass = {val f = filters; filters  = FClass((t: CtClass) => { f(t) && func(t) }, this); filters }
  def ||(func: FClass):FClass = {val f = filters; filters  = FClass((t: CtClass) => { f(t) || func(t) }, this); filters }

  protected def matchs(fun: (CtClass) => Boolean) = if(filters == null) {filters = FClass(fun, this); filters} else FClass(fun, this)

  override def evaluate(ctClass: CtClass) = if(filters!= null) filters(ctClass) else false
}

trait ClassPointCut extends MatchPointCut {
  def className(fun: (String) => Boolean) = matchs((ctClass) => fun(ctClass.getSimpleName()))
  def packageName(fun: (String) => Boolean) = matchs((ctClass) => fun(ctClass.getPackageName()))
  def superClas(className: String) = matchs((ctClass) => isSuperClass(ctClass, className))

  protected def isSuperClass(ctClass: CtClass, className: String): Boolean = {
    val superClass = ctClass.getClassPool().get(className);
    if (superClass != null) {
      ctClass.subtypeOf(superClass);
    } else {
      false
    }
  }
}

trait AnnotationPointCut extends MatchPointCut {
  def hasAnnotation(annotation: String): FClass = matchs((ctClass) => hasAnnotation(ctClass, annotation))

  protected def hasAnnotation(clazz: CtClass, annotation: String): Boolean = {
    !clazz.isAnnotation() && clazz.getAvailableAnnotations().exists(annotationProxy => {
      isImplementsAnnotation(annotationProxy.getClass().getInterfaces(), annotation)
    })
  }

  def isImplementsAnnotation[A](interfaces: Array[Class[_]], annotation: String): Boolean = {
    interfaces.exists(_.getName().equals(annotation))
  }

}
//
//  override def evaluate(ctClass: CtClass) = this.components.foldLeft(false)((c, point) => c || point.evaluate(ctClass))
//}