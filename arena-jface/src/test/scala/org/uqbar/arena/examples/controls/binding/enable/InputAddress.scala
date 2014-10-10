package org.uqbar.arena.examples.controls.binding.enable
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.uqbar.commons.utils.Observable
import org.eclipse.core.databinding.observable.list.IObservableList
import org.eclipse.core.databinding.observable.Realm
import org.uqbar.lacar.ui.model.bindings.Observables

@Observable
class Country(var name:String)

@Observable
class InputAddress {
  var country: Country = _
  var state: String = _
  var street: String = _
  var countries = Observables.newList(
		  new Country("Argentina"), 
		  new Country("Uruguay"), 
		  new Country("Bolivia"))
  
  def addCountry() : Unit = {
    countries.add(new Country("NewOne" + System.currentTimeMillis().toString))
  }
  
  def removeSelectedCountry() {
    countries.remove(country)
    country = null
  }
  
}

object InputAddress {

  def createCountries() = {
    List(
      new Country("Argentina"),
      new Country("Cuba"),
      new Country("Iran"),
      new Country("North Korea")).asJava;
  }

}