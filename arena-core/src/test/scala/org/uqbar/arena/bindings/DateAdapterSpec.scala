package org.uqbar.arena.bindings

import org.uqbar.arena.UnitSpec
import java.util.Date
import org.uqbar.commons.model.UserException

class DateAdapterSpec extends UnitSpec {

  var fechaNula : Date = null
  var fechaCualquiera = "10/10/2004"
  var fechaInvalida = "FDSFD2194"
  var dateAdapter = new DateAdapter
  
  "Una fecha cualquiera" should "poderse convertir a un string sin problemas" in {
    var result = dateAdapter.viewToModel(fechaCualquiera)
    result should be (new Date(104, 9, 10))
  }
  
  "Una fecha vacía" should "devolver nulo al transformarse a String" in {
    var result = dateAdapter.modelToView(fechaNula)
    result should be (null)
  }
  
  "Una fecha es inválida" should "throw UserException" in {
    a [UserException] should be thrownBy {
    	dateAdapter.viewToModel(fechaInvalida)
    }
  }
  
}