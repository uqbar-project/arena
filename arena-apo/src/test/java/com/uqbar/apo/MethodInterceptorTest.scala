package com.uqbar.apo
import org.junit.runner.RunWith
import com.uqbar.apo.pointcut.ClassPointCut
import com.uqbar.apo.pointcut.MethodPointCut
import com.uqbar.apo.pointcut.PointCut
import org.scalatest.junit.JUnitRunner
import javassist.Modifier
import org.scalatest.GivenWhenThen
import org.scalatest.FunSpec

import com.uqbar.aop.entities.Listener;
import com.uqbar.aop.entities.TestObject
import APODSL._


class MethodTestConfiguration extends Configuration{

  override def createAdvices() = List(new Advice(testPoincut, inteceptor))

  def testPoincut = new PointCut with ClassPointCut with MethodPointCut {
	  methodName(_ contains "update" )
    className(_ == "TestObject") && packageName(_ contains "com.uqbar.aop.entities")

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