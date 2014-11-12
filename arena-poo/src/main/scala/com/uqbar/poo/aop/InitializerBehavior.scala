package com.uqbar.poo.aop

import org.apache.commons.lang.StringUtils
import javassist.CtClass
import javassist.CtMethod
import org.uqbar.commons.utils.Dependencies
import org.uqbar.commons.utils.ReflectionUtils

trait InitializerBehavior {

  def addDependenciesInitializer(ctClass: CtClass) =
    for {
      constructor <- ctClass.getConstructors
      dependency <- dependenciesFor(ctClass)
    } constructor.insertAfter(dependency.toStatement);

  def dependenciesFor(ctClass: CtClass): Iterable[Dependency] =
    for {
      method <- ctClass.getDeclaredMethods()
      annotation <- Option(method.getAnnotation(classOf[Dependencies]))
      propertyName <- propertyName(method)
    } yield Dependency(propertyName, dependencies(annotation))

  def propertyName(method: CtMethod) =
    if (method.getName.startsWith("get") && method.getParameterTypes().isEmpty)
      Some(StringUtils.uncapitalise(method.getName.drop(3)))
    else
      None

  def dependencies(annotationProxy: Any) =
    ReflectionUtils.invokeMethod(annotationProxy, "value").asInstanceOf[Array[String]].toList;

}
