package com.uqbar.apo.bt

import org.scalatest.GivenWhenThen
import org.scalatest.FunSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.Matchers


@RunWith(classOf[JUnitRunner])
class PruebaTest extends FlatSpec with Matchers{
  
  var foo = new Foo()
  foo.getBar()
  foo.setBar("adsfsd")
  foo.getBar()  should be ("adsfsd")
  assert(false)
  
  

}

