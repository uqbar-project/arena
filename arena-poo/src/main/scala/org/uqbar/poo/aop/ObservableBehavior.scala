package org.uqbar.poo.aop;

import java.lang.reflect.Field
import org.uqbar.commons.utils.ReflectionUtils
import org.uqbar.apo.APOConfig
import org.uqbar.apo.builder.CtMethodBuilder
import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import javassist.Modifier
import javassist.NotFoundException
import javassist.bytecode.AnnotationsAttribute
import javassist.bytecode.annotation.Annotation
import org.uqbar.commons.utils.Observable

/**
 * @author nnydjesus
 *
 */
class ObservableBehavior extends InitializerBehavior {

  val eventListenerClass = java.lang.Class.forName(APOConfig.getProperty("apo.poo.propertyListener").value)

  def addBehavior(ctClass: CtClass) {
    addFieldChangeSupport(ctClass);
    addGeterChangeSupport(ctClass);
    addFirePropertyChangeMethod(ctClass);
    addAddPropertyChangeListenerMethod(ctClass);
    addRemovePropertyChangeListenerMethod(ctClass);
    addDependenciesInitializer(ctClass);
  }

  /**
   * Agrega el field changeSupport para el maejo de eventos
   * @param ctClass
   */
  protected def addFieldChangeSupport(ctClass: CtClass) {
    val fieldName = "changeSupport";
    try {
      ctClass.getField(fieldName);
    } catch {
      case e: NotFoundException => {
        val field = this.addField(ctClass, classOf[PropertySupport], fieldName, Modifier.TRANSIENT);
      }
    }
  }
  
  /**
   * Metodo que permite que un observador se registre a un evento
   * @param ctClassOwner
   */
  protected def addAddPropertyChangeListenerMethod(ctClassOwner: CtClass) {
    var ctClsString = getClass(classOf[String], ctClassOwner)
    var ctPropertyChangeListener = getClass(eventListenerClass, ctClassOwner)

    val addPropertyChangeListenerBody = "{$this.getChangeSupport().addPropertyChangeListener($argument1, $argument2);}";

    var method = new CtMethodBuilder() {
      withName("addPropertyChangeListener")
      withModifier(Modifier.PUBLIC)
      withReturnType(CtClass.voidType)
      withOwner(ctClassOwner)
      witBody(addPropertyChangeListenerBody)
      withparameterClass(ctClsString, ctPropertyChangeListener)
    }.build()

    addMethod(ctClassOwner, method);
  }

  protected def addRemovePropertyChangeListenerMethod(ctClassOwner: CtClass) {
    var ctClsString = getClass(classOf[String], ctClassOwner);
    var ctPropertyChangeListener = getClass(eventListenerClass, ctClassOwner);

    val removePropertyChangeListenerBody = "{ $this.getChangeSupport().removePropertyChangeListener($argument1, $argument2);}";

    var method = new CtMethodBuilder() {
      withName("removePropertyChangeListener")
      withModifier(Modifier.PUBLIC)
      withReturnType(CtClass.voidType)
      withOwner(ctClassOwner)
      witBody(removePropertyChangeListenerBody)
      withparameterClass(ctClsString, ctPropertyChangeListener)
    }.build()

    addMethod(ctClassOwner, method);

  }

  /**
   * Metodo que avisa a los observadores que se cambio una propiedad
   * @param ctClassOwner
   */
  protected def addFirePropertyChangeMethod(ctClassOwner: CtClass) {
    val ctClsString = getClass(classOf[String], ctClassOwner);
    val ctClsObject = getClass(classOf[Object], ctClassOwner);

    var firePropertyChangeBody = "{" +
      "$this.getChangeSupport().firePropertyChange($argument1, $argument2, $argument3);}";

    var method = new CtMethodBuilder() {
      withName("firePropertyChange")
      withModifier(Modifier.PUBLIC)
      withReturnType(CtClass.voidType)
      withOwner(ctClassOwner)
      witBody(firePropertyChangeBody)
      withparameterClass(ctClsString, ctClsObject, ctClsObject)
    }.build()

    addMethod(ctClassOwner, method);

  }

  /**
   * Geter para changeSupport, que instancia la variable si no es nula
   * @param ctClassOwner
   */
  protected def addGeterChangeSupport(ctClassOwner: CtClass) {
    val changeSuportType = getClass(classOf[PropertySupport], ctClassOwner);
    val classPool = ctClassOwner.getClassPool()

    classPool.importPackage(classOf[PropertySupport].getPackage().getName());
    classPool.importPackage(classOf[Field].getPackage().getName());
    classPool.importPackage(classOf[ReflectionUtils].getPackage().getName());
    var changeSupport = APOConfig.getProperty("apo.poo.changeSupport").value

    val getChangeSupportBody =
      "{" +
        "if($this.changeSupport == null) {" +
        "$this.changeSupport = new ${changeSupport}($this);"+
        "}" +
        "return $this.changeSupport;" +
        "}"

    val method = new CtMethodBuilder() {
      withName("getChangeSupport")
      withModifier(Modifier.PUBLIC)
      withReturnType(changeSuportType)
      withOwner(ctClassOwner)
      witBody(getChangeSupportBody.replace("${changeSupport}", changeSupport))
    }.build()
    
    try {
    	Class.forName("javax.persistence.Transient");
	    val constPool = ctClassOwner.getClassFile().getConstPool()
	    val attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
	    val annot = new Annotation("javax.persistence.Transient",constPool)
	    attr.addAnnotation(annot)
	    method.getMethodInfo().addAttribute(attr)
	}catch {
	  case e:ClassNotFoundException => //nothing to do 
	}

    addMethod(ctClassOwner, method);

  }
  
  //HELPPERS

  protected def addMethod(owner: CtClass, method: CtMethod) {
    if (!owner.getMethods().contains(method)) {
      owner.addMethod(method);
    }
  }

  def getClass(aClass: Class[_], ctClass: CtClass): CtClass = getClass(aClass.getName(), ctClass)

  def getClass(className: String, ctClass: CtClass) = ctClass.getClassPool().get(className);

  protected def addField(owner: CtClass, fieldClass: Class[_], name: String, modifier: Int): CtField = {
    var ctField = new CtField(getClass(fieldClass.getName(), owner), name, owner);
    ctField.setModifiers(modifier);
	    if (!owner.getFields().contains(ctField)) {
	      owner.addField(ctField);
	    }
    ctField
  }

}

