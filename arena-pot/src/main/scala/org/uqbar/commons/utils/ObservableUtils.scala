package org.uqbar.commons.utils

import org.uqbar.commons.model.ObservableUtils
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeEvent
import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

case class When(val obj:Any){
  def change(property:String) = Change(property, this)
}
case class Change(property:String, val when:When){
  val properties = Buffer[String](property)
  def and(property:String) = {
    properties.append(property)
    this
  }
  def fireChange(property:String) = FireChange(property, this)
}
case class FireChange(val property:String, val change:Change){
  def of(obj:Any) {
    ObservableUtils.addPropertyListener(change.when.obj, new PropertyChangeListener(){
    	def propertyChange(evt:PropertyChangeEvent){
    	   val fieldValue = ReflectionUtils.readField(obj, property)
    	   if(fieldValue != null)  ObservableUtils.firePropertyChanged(obj, property, fieldValue); 
    	}
    }, change.properties)
  }
}
