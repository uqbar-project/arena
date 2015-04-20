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

class ObservableFieldInterceptor extends FieldInterceptor with FieldInfo {
  propertyKey = "ObservableFieldAccessInterceptor";

  read((statement, field) => {
    val methodInfo = field.where().getMethodInfo()
    if (isGetter(field.where())) {
      var dependency = propertyNameFromGetter(methodInfo.getName())
      val beforeRead = "$this.getChangeSupport().addDependency($class.getName(), $S$fieldName$S, $S" + dependency + "$S);"
      statement.insert(0, beforeRead)
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

class DependencyFieldInterceptor extends MethodCallInterceptor with FieldInfo {
  propertyKey = "DependencyFieldInterceptor";

  after(mc => {
    val method = mc.where()
    val dependency = propertyNameFromGetter(method.getMethodInfo().getName())
    var property = propertyNameFromGetter(mc.getMethodName())
    val className = method.getDeclaringClass().getName()

    for {
      constructor <- method.getDeclaringClass().getConstructors
    } constructor.insertAfter(APOParser.parse("$this.getChangeSupport().addDependency($class.getName(), $S" + property + "$S, $S" + dependency + "$S);"))
      
    ""
  })

}
