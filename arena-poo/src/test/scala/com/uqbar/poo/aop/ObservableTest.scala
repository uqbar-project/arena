package com.uqbar.poo.aop

import java.beans.PropertyChangeListener
import org.junit.Before
import org.junit.Test
import org.scalatest.BeforeAndAfter
import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import org.scalatest.junit.JUnitRunner
import org.uqbar.commons.utils.Observable
import com.uqbar.apo.APOConfig
import org.uqbar.commons.utils.ReflectionUtils
import java.beans.PropertyChangeEvent
import java.lang.reflect.Method
import org.junit.Assert

@Observable
case class Occupation(var name: String = "") {}

@Observable
case class ModelObject(var name: String = "", var lastName: String = "", var occupation: Occupation = Occupation()) {

  def getFullName() = name + " " + lastName
  def description() = getFullName() + " - " + occupation.name
}

class ObservableTest extends PropertyChangeListener {

  var model: ModelObject = _
  var methodListener: Method = _
  var event: PropertyChangeEvent = _

  @Before
  def setup() {
    model = ModelObject()
    methodListener = ReflectionUtils.findMethod(classOf[ModelObject], "addPropertyChangeListener", true,
      Array(classOf[String], classOf[PropertyChangeListener]))
  }

  def addListener(event: String) = methodListener.invoke(model, event, this)

  @Test
  def fireEventWhenChangeProperty() {
    addListener("name")

    model.name = "Pepe"
    assertEvent("name", "Pepe")
  }

  @Test
  def fireEventWhenChangeDependendyProperty() {
    addListener("fullName")

    model.name = "Pepe"
    assertEvent("fullName", "Pepe ")

    model.lastName = "Pérez"
    assertEvent("fullName", "Pepe Pérez")
  }

  @Test
  def fireEventWhenChangeDependendyPropertySecondLevel() {
    addListener("description")

    model.name = "Pepe"
    assertEvent("description", "Pepe  - ")
  }

  @Test
  def fireEventWhenChangeDependendyNestedProperty() {
    addListener("description")

    model.occupation.name = "Teacher"
    assertEvent("description", "  - Teacher")
  }
  
  def assertEvent(propertyName:String, value:String){
    Assert.assertEquals(event.getPropertyName(), propertyName)
    Assert.assertEquals(event.getNewValue(), value)
  }

  def propertyChange(event: PropertyChangeEvent) = this.event = event
}