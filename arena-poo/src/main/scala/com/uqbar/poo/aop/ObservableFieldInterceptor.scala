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

class ObservableFieldInterceptor extends FieldInterceptor with FieldInfo with AddDependencyInConstructor{
  propertyKey = "ObservableFieldAccessInterceptor";

  read((statement, field) => {
    val methodInfo = field.where().getMethodInfo()
    if (isGetter(field.where())) {
      var dependency = propertyNameFromGetter(methodInfo.getName())
      addDependency(field.where().getDeclaringClass(), field.getFieldName(), dependency)
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

class DependencyFieldInterceptor extends MethodCallInterceptor with FieldInfo with AddDependencyInConstructor{
  propertyKey = "DependencyFieldInterceptor";

  after(mc => {
    val method = mc.where()
    val dependency = propertyNameFromGetter(method.getMethodInfo().getName())
    var property = propertyNameFromGetter(mc.getMethodName())
    addDependency(method.getDeclaringClass(), property, dependency)
    ""
  })

}

trait AddDependencyInConstructor{
  
  def addDependency(ctClass:CtClass, property:String, dependency:String){
    if(!property.equals(dependency)){
    	ctClass.getConstructors.foreach(_.insertAfter(APOParser.parse(
    	    "$this.getChangeSupport().addDependency($class.getName(), $S" + property + "$S, $S" + dependency + "$S);")))
    }
  }
}
