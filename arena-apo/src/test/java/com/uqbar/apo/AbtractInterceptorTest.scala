package com.uqbar.apo

import com.uqbar.aop.entities.Listener
import scala.collection.mutable.Buffer
import com.uqbar.aop.entities.TestObject

trait AbstractInterceptorTest extends Listener {

  var eventDispatch: Buffer[String] = _

  def listen(event: String) = eventDispatch.append(event)
  var testObject: TestObject = _
  
  def key:String
  
  
  @org.junit.Before
  def setup() {
    eventDispatch = Buffer()
    testObject = new TestObject()
    testObject.addListener(key, this)
  }

}