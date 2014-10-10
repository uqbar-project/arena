package com.uqbar.poo.aop

import java.util.EventListener;

trait PropertySupport {

	def firePropertyChange(propertyName:String , oldValue:Object, newValue:Object )

	def addPropertyChangeListener(propertyName:String , listener:EventListener)

	def removePropertyChangeListener(propertyName:String, listener:EventListener)

}