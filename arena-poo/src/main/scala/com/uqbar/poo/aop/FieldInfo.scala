package com.uqbar.poo.aop

import javassist.NotFoundException
import javassist.CtBehavior
import javassist.CtClass
import org.apache.commons.lang.StringUtils
import javassist.ClassPool

trait FieldInfo {

  def isGetter(behaviour: CtBehavior): Boolean = {
    if (behaviour.getDeclaringClass().isFrozen()) behaviour.getDeclaringClass().defrost()
    val method = behaviour.getDeclaringClass().getMethod(behaviour.getMethodInfo().getName(),
      behaviour.getMethodInfo().getDescriptor())
    method.getParameterTypes().length == 0 &&
      !method.getReturnType().equals(ClassPool.getDefault().getCtClass(Void.TYPE.getName())) &&
      (method.getName().startsWith("get") ||
        method.getName().startsWith("is") ||
        hasField(behaviour.getDeclaringClass(), method.getName()));
  }

  def hasField(ctClass: CtClass, fieldName: String): Boolean = {
    try {
      ctClass.getField(fieldName)
      true
    } catch {
      case e: NotFoundException => false
    }
  }

  def propertyNameFromGetter(methodName: String): String = {
    println(s"MethodName $methodName")
    if (methodName.startsWith("get"))
      return StringUtils.uncapitalise(methodName.drop(3))
    if (methodName.startsWith("is"))
      return StringUtils.uncapitalise(methodName.drop(2))
    if (methodName.endsWith("_$eq"))
      return methodName.dropRight(4)
    methodName
  }
}