package org.uqbar.poo.aop

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FeatureSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.GivenWhenThen
import org.uqbar.commons.model.utils.ReflectionUtils
import org.uqbar.commons.model.annotations.Observable

@Observable
case class Bean(var name:String){
  def getName()= name
  def setName(aName:String) = name = aName
}


class ObservableTest extends FeatureSpec with GivenWhenThen with BeforeAndAfter {
  
    scenario("se invoca el metodo pop en un stack no vacio") {
 
      given("un stack no vacio")
      val bean = Bean("Pepe") 
 
      when("cuando se invoca pop en el stack")
  		val support:PropertySupport = ReflectionUtils.invokeMethod(bean, "getChangeSupport").asInstanceOf[PropertySupport];
 
      then("Deberia retornar el ultimo elemento pusheado")
      assert(support != null)
    }
 

}