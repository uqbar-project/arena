package org.uqbar.arena.tests.nestedCombos

import scala.collection.JavaConversions.asScalaBuffer

import org.uqbar.commons.utils.Observable

@Observable
class Country(var name: String) {
  var provinceName = ""
  var provinces: java.util.List[Province] = new java.util.ArrayList[Province]()
  var provinceSelected:Province=_
  
  def addProvince(p:String) = provinces.add(new Province(p))
  def addProvince():Unit = provinces.add(new Province(provinceName))
  def removeProvince():Unit = provinces.-=(provinceSelected);provinceSelected=null
}
