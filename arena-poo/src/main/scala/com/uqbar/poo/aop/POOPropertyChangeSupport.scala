package com.uqbar.poo.aop

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.util.EventListener
import scala.collection.mutable.Map
import org.uqbar.commons.utils.ReflectionUtils
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.uqbar.commons.model.ScalaBeanInfo
import scala.collection.mutable.Buffer
import com.uqbar.apo.APOConfig

@SerialVersionUID(1L)
class POOPropertyChangeSupport(sourceBean: AnyRef) extends PropertyChangeSupport(sourceBean) with PropertySupport {

  var source: AnyRef = sourceBean
  val bean = new ScalaBeanInfo(source.getClass())

  var mapping = Map[String, Buffer[String]]()
  var mmm = Map[String, Tuple2[String, String]]()

  def addDependency(className:String, property: String, dependency: String) {
    val values = dependencies(property)
    if (className.equals(source .getClass.getName) && bean.getPropertyDescriptor(property) != null ) {
      if (!values.contains(dependency)) {
        values.add(dependency)
//        println(" dependency: " + dependency + " to " + property)
      }
    } else {
      val properies = mapping.flatMap { case (k, v) => if(v.contains(dependency)) Some(k) else None }
      properies.foreach(value => {
        if (bean.getPropertyDescriptor(value) != null) {
          mmm(value) = (property, dependency)
//          println(" relation to " + value + " with " + property)
          addListener(value, property, dependency)
        }
      })
    }

  }

  protected def dependencies(property: String): Buffer[String] = {
    if (!mapping.containsKey(property)) {
      mapping.put(property, Buffer())
    }
    mapping(property)
  }

  def dependenciesFor(clazz: String, property: String): Buffer[String] = dependencies(property)

  override def firePropertyChange(propertyName: String, oldValue: AnyRef, newValue: AnyRef) {
    super.firePropertyChange(this.convertProperty(propertyName), oldValue, newValue)
//    println("Fire: " + propertyName)
    val dependencies = this.dependenciesFor(source.getClass.getName, propertyName)
    for (dependency <- dependencies if dependency != propertyName &&
        ScalaBeanInfo.getPropertyDescriptor(source, dependency) != null) {
      firePropertyChange(dependency, null, ReflectionUtils.invokeGetter(source, dependency))
    }
  }
  
  def addListener(sourceProperty:String, sourceDependency:String, property:String){
    val sourceObject = ReflectionUtils.invokeGetter(source, sourceProperty)
    if(sourceObject != null){
      val methodListener = ReflectionUtils.findMethod(sourceObject.getClass(), "addPropertyChangeListener", false,
      Array(classOf[String], Class.forName(APOConfig.getProperty("apo.poo.propertyListener").value)))
      
      if(methodListener != null){
        methodListener.invoke(sourceObject, sourceDependency, new PropertyChangeListener(){
          
          def propertyChange(event: PropertyChangeEvent) {
             firePropertyChange(property, event.getOldValue(),  ReflectionUtils.invokeGetter(source, property))
          }
        })
      }
    }
  }

  def addPropertyChangeListener(propertyName: String, listener: EventListener) {
    super.addPropertyChangeListener(propertyName, listener.asInstanceOf[PropertyChangeListener])
  }

  def removePropertyChangeListener(propertyName: String, listener: EventListener) {
    super.removePropertyChangeListener(propertyName, listener.asInstanceOf[PropertyChangeListener])
  }

  protected def convertProperty(propertyName: String): String = {
    if (propertyName.startsWith("_")) {
      propertyName.drop(1)
    } else {
      propertyName
    }
  }

  override def fireNestedPropertyChange(event: PropertyChangeEvent) {
  }
}
