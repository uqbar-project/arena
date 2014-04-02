package org.uqbar.arena.test.scala.collections.binding

import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.MainWindow
import org.uqbar.commons.utils.Observable
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.Label
import scala.collection.mutable.ArrayBuffer
import org.uqbar.arena.widgets.{ List => Lista }

object TableWithScalaListWindow extends MainWindow(ProgramacionFox) with App {
  startApplication()
  
  override def createContents(p:Panel) = {
    p.setLayout(new VerticalLayout())
    
	// programas
    val t = new Table(p, classOf[Programa])
    t.bindItemsToProperty("programas")
    t.bindSelectionToProperty("programaSeleccionado")
    	val c = new Column(t)
    	c.setTitle("Nombre")
    	c.bindContentsToProperty("nombre")
    
    new Label(p).bindValueToProperty("programaSeleccionado.nombre")
    	
    // personajes
    val tablePersonajes = new Table(p, classOf[Personaje])
    tablePersonajes.bindItemsToProperty("programaSeleccionado.personajes")
   	val nombrePersonaje = new Column(tablePersonajes)
    	nombrePersonaje.setTitle("Nombre")
    	nombrePersonaje.bindContentsToProperty("nombre")
    	
    println("programas " + getModelObject.programas)
    
    val listaDias = new Lista(p)
    listaDias.bindItemsToProperty("programaSeleccionado.dias")
  }

}

// modelo

object ProgramacionFox extends Programacion {
  println("creando programacion de fox...")
  programas = List(
      new Programa("Los Simpsons") {
    	  personajes = Set("Homero Simpson", "Marge Simpson", "Bart Simpson", "Lisa Simpson", "Maggie Simpson", "Apu").map(new Personaje(_))
    	  dias ++= List("lunes", "martes", "miercoles")
      }, 
      new Programa("Futurama") {
    	  personajes = Set("Fry", "Bender Bending Rodriguez", "Pr. Farnsworth", "Amy Wong", "Zapp Brannigan", "Dr Zoidberg").map(new Personaje(_))
    	  dias ++= List("sabados", "domingos")
      }, 
      new Programa("Family Guy") {
    	  personajes = Set("Peter Griffin", "Lois  Griffin", "Meg  Griffin", "Chris  Griffin", "Stewie  Griffin", "Brian Griffin").map(new Personaje(_))
    	  dias ++= List("lunes", "miercoles", "viernes")
      }
  )
}

@Observable
class Programacion {
  // Ejemplo con immutable List
  var programas : List[Programa] = _
  var programaSeleccionado : Programa = _
}

@Observable
class Programa(var nombre : String) {
  // Ejemplo con immutable Set
  var personajes : Set[Personaje] = _
  var dias = ArrayBuffer[String]()
}

@Observable 
class Personaje(var nombre : String)

