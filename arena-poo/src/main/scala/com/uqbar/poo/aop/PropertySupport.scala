package com.uqbar.poo.aop

import java.util.EventListener;
import java.beans.PropertyChangeEvent

trait PropertySupport {

  def firePropertyChange(propertyName: String, oldValue: Object, newValue: Object)

  def addPropertyChangeListener(propertyName: String, listener: EventListener)

  def removePropertyChangeListener(propertyName: String, listener: EventListener)

  def addDependency(className:String, property: String, dependency: String)
  
  def fireNestedPropertyChange(event: PropertyChangeEvent)

}