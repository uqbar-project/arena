package org.uqbar.arena.tests.nestedCombos

import org.uqbar.commons.utils.TransactionalAndObservable
import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

@TransactionalAndObservable
class Country(var name: String) {
  var provinceName = ""
  var provinces: java.util.List[Province] = Buffer[Province]()
  var provinceSelected:Province=_
  
//  def this(a:String){
//    this()
    //provinces = Buffer[Province]()
//  }
  
  def addProvince(p:String) = provinces.add(new Province(provinceName))
  def addProvince():Unit = provinces.add(new Province(provinceName))
  def removeProvince() = provinces.-=(provinceSelected);provinceSelected=null
  def aaadsds = "asfdasdf"
}
