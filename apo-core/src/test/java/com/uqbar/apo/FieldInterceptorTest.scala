package com.uqbar.apo

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import javassist.ClassPool
import org.junit.runner.RunWith
import javassist.expr.FieldAccess
import com.uqbar.apo.pointcut.FieldPointCut
import com.uqbar.apo.pointcut.ClassPointCut
import com.uqbar.apo.pointcut.MatchPointCut
import com.uqbar.apo.pointcut.PointCut
import com.uqbar.aop.entities.Listener;
import com.uqbar.aop.entities.TestObjectt
import org.scalatest.GivenWhenThen
import APODSL._



class FieldTestConfiguration extends Configuration {

  override def createAdvices() = List(new Advice(testPoincut, interceptor))

  def testPoincut = new PointCut with ClassPointCut with FieldPointCut {
    fieldType[String]
    className(_ == "TestObjectt") && packageName(_ contains "com.uqbar.aop.entities") 
  }
  def interceptor = field.write((statement, fieldAccess) => statement.append("$this.dispatch(String.valueOf($argument1));"))
}

@RunWith(classOf[JUnitRunner])
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
