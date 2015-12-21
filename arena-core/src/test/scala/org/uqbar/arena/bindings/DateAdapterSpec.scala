package org.uqbar.arena.bindings

import org.uqbar.arena.UnitSpec
import java.util.Date
import org.uqbar.commons.model.UserException

class dateTransformerSpec extends UnitSpec {

  var fechaNula : Date = null
  var fechaCualquiera = "10/10/2004"
  var fechaInvalida = "FDSFD2194"
  var dateTransformer = new DateTransformer
  
  "Una fecha cualquiera" should "poderse convertir a un string sin problemas" in {
    var result = dateTransformer.viewToModel(fechaCualquiera)
    result should be (new Date(104, 9, 10))
  }
  
  "Una fecha vacía" should "devolver nulo al transformarse a String" in {
    var result = dateTransformer.modelToView(fechaNula)
    result should be (null)
  }
  
  "Una fecha es inválida" should "throw UserException" in {
    a [UserException] should be thrownBy {
    	dateTransformer.viewToModel(fechaInvalida)
    }
  }
  
}