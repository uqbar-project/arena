package com.uqbar.apo

import scala.collection.mutable.Buffer

import org.junit.Assert
import org.junit.Test

import com.uqbar.apo.pointcut.ClassPointCut
import com.uqbar.apo.pointcut.FieldPointCut
import com.uqbar.apo.pointcut.PointCut

import APODSL.field

class FieldTestConfiguration extends Configuration {

  override def createAdvices() = List(new Advice(testPoincut, interceptor))

  def testPoincut = new FieldPointCut with ClassPointCut {
    fieldType[String]
    className(_ == "TestObject") && packageName(_ contains "com.uqbar.aop.entities")
  }
  def interceptor = field.write((statement, fieldAccess) => 
    statement.append("$this.dispatch($SFieldInterceptorTest$S, String.valueOf($argument1));"))
}

class FieldInterceptorTest extends AbstractInterceptorTest {
  
  def key = "FieldInterceptorTest"

  @Test
  def disparaUnEventoCuandoSeModificaUnaPropiedad() {
    testObject.setName("Juan")
    Assert.assertEquals(Buffer("Juan"), eventDispatch)
    testObject.setLastName("Pepe")
    Assert.assertEquals(Buffer("Juan", "Pepe"), eventDispatch)
  }
}
