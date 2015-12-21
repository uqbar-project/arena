package org.uqbar.apo
import org.junit.runner.RunWith

import org.uqbar.apo.pointcut.ClassPointCut
import org.uqbar.apo.pointcut.MethodPointCut
import org.uqbar.apo.pointcut.PointCut
import org.scalatest.junit.JUnitRunner
import javassist.Modifier
import org.scalatest.GivenWhenThen
import org.scalatest.FunSpec
import org.uqbar.aop.entities.Listener
import org.uqbar.aop.entities.TestObject

import APODSL._


class MethodTestConfiguration extends Configuration{

  override def createAdvices() = List(new Advice(testPoincut, inteceptor))

  def testPoincut = new PointCut with ClassPointCut with MethodPointCut {
	  methodName(_ contains "update" )
    className(_ == "TestObject") && packageName(_ contains "org.uqbar.aop.entities")

  }

  def inteceptor = method
    .after((method) => "$this.dispatch(\"" + method.getName + "\");")
    .before(method => "System.out.println(\"" + method.getName + "\");")
}

class MethodInterceptorTest extends FunSpec with GivenWhenThen with Listener {



  var eventDispatch: String = _

  def listen(event: String) = eventDispatch = event

  describe("Probamos interceptar metodos") {
    it("") {

      Given("un objeto de prueba")
      val testObject = new TestObject("Pepe", "pp@p.p")
      testObject.addListener(this)

      When("Cuando se invoca un metodo updateName")
      testObject.updateName("Juan")

      Then("Deberia haber tirado el evento 'updateName'")
      assert(eventDispatch === "updateName")

      When("Cuando se invoca el metodo updateFatherName ")
      testObject.updateFatherName("Pepe")

      Then("Deberia haber tirado el evento 'updateFatherName'")
      assert(eventDispatch === "updateFatherName")
    }
  }
}