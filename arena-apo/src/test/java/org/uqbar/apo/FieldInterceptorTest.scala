package org.uqbar.apo

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec

import javassist.ClassPool

import org.junit.runner.RunWith

import javassist.expr.FieldAccess

import org.uqbar.apo.pointcut.FieldPointCut
import org.uqbar.apo.pointcut.ClassPointCut
import org.uqbar.apo.pointcut.MatchPointCut
import org.uqbar.apo.pointcut.PointCut
import org.uqbar.aop.entities.Listener;
import org.uqbar.aop.entities.TestObjectt

import org.scalatest.GivenWhenThen
import org.uqbar.apo.Advice

import APODSL._

class FieldTestConfiguration extends Configuration {

  override def createAdvices() = List(new Advice(testPoincut, interceptor))

  def testPoincut = new PointCut with ClassPointCut with FieldPointCut {
    fieldType[String]
    className(_ == "TestObjectt") && packageName(_ contains "org.uqbar.aop.entities") 
  }
  def interceptor = field.write((statement, fieldAccess) => statement.append("$this.dispatch(String.valueOf($argument1));"))
}

class FieldInterceptorTest extends FunSpec with GivenWhenThen with Listener {

  var eventDispatch: String = _

  def listen(event: String) = eventDispatch = event

  describe("Probamos interceptar los fields") {
    it("") {

      Given("un objeto de prueba")
      val testObject = new TestObjectt("Pepe", "pp@p.p")
      testObject.addListener(this)

      When("Cuando se invoca el setter del nombre")
      testObject.name = "Juan"

      Then("Deberia haber tirado el evento 'Juan'")
      assert(eventDispatch === "Juan")

      When("Cuando se invoca un el setter del fatherName")
      testObject.fatherName = "Pepe"

      Then("Deberia haber tirado el evento 'Pepe'")
      assert(eventDispatch === "Pepe")
    }
  }
}
