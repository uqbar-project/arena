package org.uqbar.arena.examples.controls.binding.enable

import org.uqbar.commons.utils.TransactionalAndObservable

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

@TransactionalAndObservable
class SAdress {
  var country: Country = _
  var state: String = _
  var street: String = _
  
  def getCountries() =  List(
      new Country("Argentina"),
      new Country("Cuba"),
      new Country("Iran"),
      new Country("North Korea")).asJava;
 

}

object SAdress {

  def createCountries() = {
    List(
      new Country("Argentina"),
      new Country("Cuba"),
      new Country("Iran"),
      new Country("North Korea")).asJava;
  }

}