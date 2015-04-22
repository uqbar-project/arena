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
      Array(classOf[String], Class.forName(APOConfig.getProperty("apo.poo.propertyListener").value)))
  }

  def addListener(event: String) = methodListener.invoke(model, event, this)

  @Test
  def fireEventWhenChangeProperty() {
    addListener("name")

    model.name = "Pepe"
    Assert.assertEquals(event.getPropertyName(), "name")
    Assert.assertEquals(event.getNewValue(), "Pepe")
  }

  @Test
  def fireEventWhenChangeDependendyProperty() {
    addListener("fullName")

    model.name = "Pepe"
    Assert.assertEquals(event.getPropertyName(), "fullName")
    Assert.assertEquals(event.getNewValue(), "Pepe ")

    model.lastName = "Pérez"
    Assert.assertEquals(event.getPropertyName(), "fullName")
    Assert.assertEquals(event.getNewValue(), "Pepe Pérez")
  }

  @Test
  def fireEventWhenChangeDependendyPropertySecondLevel() {
    addListener("description")

    model.name = "Pepe"
    Assert.assertEquals(event.getPropertyName(), "description")
    println(model.description)
    Assert.assertEquals(event.getNewValue(), "Pepe  - ")
  }

  @Test
  def fireEventWhenChangeDependendyNestedProperty() {
    addListener("description")

    model.occupation.name = "Teacher"
    Assert.assertEquals(event.getPropertyName(), "description")
    println(model.description)
    Assert.assertEquals(event.getNewValue(), "  - Teacher")
  }

  def propertyChange(event: PropertyChangeEvent) = this.event = event
}