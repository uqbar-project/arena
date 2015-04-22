package com.uqbar.poo.aop

import javassist.Modifier
import com.uqbar.apo.FieldInterceptor
import com.uqbar.apo.parser._
import org.apache.commons.lang.StringUtils
import com.uqbar.apo.MethodCallInterceptor
import javassist.CtMethod
import org.uqbar.commons.model.ScalaBeanInfo
import javassist.CtClass
import javassist.ClassPool
import javassist.CtBehavior
import javassist.bytecode.MethodInfo
import javassist.NotFoundException

/**
 * @author nny
 *
 */

class ObservableFieldInterceptor extends FieldInterceptor with FieldInfo with AddDependencyInConstructor {
  propertyKey = "ObservableFieldAccessInterceptor";

  read((statement, field) => {
    val methodInfo = field.where().getMethodInfo()
    if (isGetter(field.where())) {
      val dependency = propertyNameFromGetter(methodInfo.getName())
      val clazz = field.where().getDeclaringClass()
      if (dependency != field.getFieldName()) addDependency(clazz, clazz.getName(), field.getFieldName(), dependency)
    }
  })

  write((statement, fieldAccess) => {
    val newStatement =
      """
		  $fieldTypeName oldValue = $oldValue;
		  $originalAsigment;
		  $this.firePropertyChange($S$fieldName$S, $coerceToObject(oldValue), $coerceToObject($newValue));
    	"""
    statement.replace(newStatement)
  })

}

class DependencyFieldInterceptor extends MethodCallInterceptor with FieldInfo with AddDependencyInConstructor {
  propertyKey = "DependencyFieldInterceptor";

  after(mc => {
    val method = mc.where()
    val dependency = propertyNameFromGetter(method.getMethodInfo().getName())
    var property = propertyNameFromGetter(mc.getMethodName())
    addDependency(method.getDeclaringClass(), mc.getClassName(), property, dependency)
    ""
  })

}

trait AddDependencyInConstructor {

  def addDependency(ctClass: CtClass, className: String, property: String, dependency: String) {
    ctClass.getConstructors.foreach(_.insertAfter(APOParser.parse(
      "$this.getChangeSupport().addDependency($S" + className + "$S,$S" + property + "$S, $S" + dependency + "$S);")))
  }
}
