package com.uqbar.apo

import scala.collection.mutable.Buffer

import org.junit.Assert
import org.junit.Test

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

  def inteceptor = method.after((method) => dispatch(method.getName()))

  def testCallPointcut = new PointCut with ClassPointCut with MethodCallPointCut {
    methodName(cm => cm.startsWith("get") && cm.length > 3) && notConstructor
    className(_ == "TestObject") && packageName(_ contains "com.uqbar.aop.entities")
  }

  def callInteceptor = methodCall.before(method => dispatch(method.getMethodName()))

  def dispatch(name: String) = "$this.dispatch($SMethodInterceptorTest$S, $S" + name + "$S);"
}

class MethodInterceptorTest extends AbstractInterceptorTest {

  def key = "MethodInterceptorTest"

  @Test
  def disparaUnEventoCuandoSeInvocaUnSetter() {
    testObject.setName("Juan")
    Assert.assertEquals(eventDispatch, Buffer("setName"))
    testObject.setLastName("Pepe")
    Assert.assertEquals(eventDispatch, Buffer("setName", "setLastName"))
  }

  @Test
  def dispararUnEventoCuandoSeUsaUnGetterEnUnMetodo() {
    testObject.description()
    Assert.assertEquals(eventDispatch, Buffer("getFullName"))
  }

}