package org.uqbar.arena.tests.nestedCombos
import java.util.List
import org.uqbar.commons.utils.Observable
import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConversions._

@Observable
class Country(var name: String) {
  var provinces: List[Province] = new ArrayBuffer[Province]

  def addProvince(provinceName: String) = provinces += new Province(provinceName)
}
