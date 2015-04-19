package com.uqbar.apo

import scala.collection.mutable.Buffer

import org.junit.Assert
import org.junit.Test

import com.uqbar.aop.entities.Listener
import com.uqbar.aop.entities.TestObject
import com.uqbar.apo.pointcut.ClassPointCut
import com.uqbar.apo.pointcut.MethodCallPointCut
import com.uqbar.apo.pointcut.MethodPointCut
import com.uqbar.apo.pointcut.PointCut

import APODSL.method
import APODSL.methodCall

class MethodTestConfiguration extends Configuration {

  override def createAdvices() = List(new Advice(testPoincut, inteceptor), new Advice(testCallPointcut, callInteceptor))

  def testPoincut = new PointCut with ClassPointCut with MethodPointCut {
    methodName(_ startsWith ("set"))
    className(_ == "TestObject") && packageName(_ contains "com.uqbar.aop.entities")
  }

  def inteceptor = method
    .after((method) => "$this.dispatch(\"" + method.getName + "\");")
    .before(method => "System.out.println(\"" + method.getName + "\");")

  def testCallPointcut = new PointCut with ClassPointCut with MethodCallPointCut {
    methodName(cm => cm.startsWith("get")) && notConstructor
    className(_ == "TestObject") && packageName(_ contains "com.uqbar.aop.entities")
  }

  def callInteceptor = methodCall
    .before(
      (method) => "$this.dispatch(\"" + method.getMethodName()+"\");")

}

class MethodInterceptorTest extends Listener {

  var eventDispatch:Buffer[String]= _

  def listen(event: String) = eventDispatch.append(event)
  var testObject: TestObject = _

  @org.junit.Before
  def setup() {
    eventDispatch  = Buffer()
    testObject = new TestObject("Pepe", "pp@p.p")
    testObject.addListener(this)
  }

  @Test
  def disparaUnEventoCuandoSeInvocaUnSetter() {
    testObject.setName("Juan")
    Assert.assertEquals(eventDispatch, Buffer("setName"))
    testObject.setLastName("Pepe")
    Assert.assertEquals(eventDispatch, Buffer("setName", "setLastName"))
  }

  @Test
  def dispararUnEventoCuandoSeUsaUnGetterEnUnMetodo() {
    testObject.nothing()
    Assert.assertEquals(eventDispatch, Buffer("getNothig"))
  }

}