package org.uqbar.arena.widget.traits

import scala.collection.mutable.ArrayBuffer
import org.mockito.internal.InternalMockHandler
import org.mockito.invocation.Invocation
import org.uqbar.arena.scala.ArenaScalaImplicits

/**
 * MockHandler implementation for arena type-safe bindings.
 *
 * A lot of work is still needed here.
 *
 * @author jfernandes
 */
//TODO: move to other package
class BindingMockHandler extends InternalMockHandler[Object] {
  var invocationChains = new ArrayBuffer[Invocation]

  override def handle(invocation: Invocation) = {
    val m = invocation.getMethod
    if (m.getName != "getClass") {
      this.invocationChains += invocation
    }
    ArenaScalaImplicits.createMockFor(m.getReturnType, this).asInstanceOf[Object]
  }
  
  def buildPropertyExpression() = {
    invocationChains.tail.foldLeft(toPropertyName(invocationChains head)) { (r, i) => r + "." + toPropertyName(i)} 
  }
  
  def toPropertyName(invocation:Invocation) : String = toPropertyName(invocation.getMethod.getName)

  def toPropertyName(methodName: String): String = {
    // es una truchada todo esto
    if (methodName.startsWith("get")) {
      val propName = methodName.substring(3)
      Character.toLowerCase(propName.charAt(0)) + propName.substring(1)
    } else methodName
  }

  override def getInvocationContainer() = {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override def getMockSettings() = {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override def setAnswersForStubbing(a: java.util.List[org.mockito.stubbing.Answer[_]]): Unit = {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

  override def voidMethodStubbable(mock: Object) = {
    throw new UnsupportedOperationException("TODO: auto-generated method stub")
  }

}