package org.uqbar.arena.tests.bindings

import java.awt.Color
import java.util.HashMap
import java.util.Map
import org.uqbar.arena.actions.MessageSend
import org.uqbar.arena.bindings.PropertyAdapter
import org.uqbar.arena.bindings.transformers.AbstractReadOnlyTransformer
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.List
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.RadioSelector
import org.uqbar.arena.widgets.Selector
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.MainWindow
import org.uqbar.lacar.ui.model.Action
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.layout.ColumnLayout
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.uqbar.arena.test.scala.collections.binding.TableWithScalaListWindow
import org.uqbar.arena.scala.ArenaScalaImplicits._
import org.uqbar.lacar.ui.model.ControlBuilder
import org.uqbar.lacar.ui.model.bindings.ViewObservable
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.MainWindow
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.MainWindow
import org.uqbar.arena.tests.nestedColums.Estudent
import org.uqbar.arena.tests.nestedColums.EstudenStatus
import org.uqbar.arena.tests.nestedColums.University


/**
 * Demuestra distintas formas de binding tipado en scala.
 *
 */
object BindingExampleWindow extends MainWindow[University](University.university) with App {
	startApplication()
	
	override def createContents(mainPanel:Panel) {
		mainPanel.setLayout(new VerticalLayout())
		
		val table = new Table[Estudent](mainPanel, classOf[Estudent])
		table setWidth(200)
		table setHeight(200)
		table.items() <=> getModelObject()@@{ _ students }
		table.value() <=> getModelObject()@@{ _ currentEstudent }
		
		val statusTransomer = new Transformer[EstudenStatus.Status, Color]() {
			def transform(status:EstudenStatus.Status) =  status match {
		        case EstudenStatus.EXPELLED => Color.RED
		        case EstudenStatus.FREE => Color.ORANGE
		        case EstudenStatus.REGULAR => Color.BLUE
		    }
	    }
		
		val nameColumn = new Column[Estudent](table)
		nameColumn setTitle("Name")	bindContentsToProperty("name") setTransformer { name:String => name toUpperCase }
		nameColumn bindBackground("status", statusTransomer)
		
		val departmentColumn = new Column[Estudent](table)
		departmentColumn.setTitle("Department").bindContentsToProperty("department.name")
		departmentColumn.bindBackground("status", statusTransomer)
		
		val formPanel = new Panel(mainPanel)
		formPanel.setLayout(new ColumnLayout(2))
		
		new Label(formPanel) setText("Nombre")
		val nameTextbox = new TextBox(formPanel)
		nameTextbox.value() <=> getModelObject@@{ _.currentEstudent.name }
		(nameTextbox.background() <=> {u:University => u.currentEstudent.status } )
		   .setModelToView { (e : EstudenStatus.Status) => e match {
		      case EstudenStatus.EXPELLED => Color.RED
		      case EstudenStatus.FREE => Color.ORANGE
		      case EstudenStatus.REGULAR => Color.BLUE
			}
		};
		

		
		new Label(formPanel) setText("Status")
		val provinces = new Selector[EstudenStatus.Status](formPanel)
		provinces.setContents(EstudenStatus.values.toList, "name")

		// Different kinds of bindings
		provinces.bindValueToProperty("currentEstudent.status")
		provinces.bindValueTo { u: University => u.currentEstudent.status }
		provinces.bind(provinces.value(), { u: University => u.currentEstudent.status })
		provinces.value() <=> getModelObject@@{ _.currentEstudent.status }
	}
		
	
	// OTRAS FORMAS de binding (work-in-progress)
	
//		nameTextbox.bindBackground("currentEstudent.status").setModelToView(statusTransomer)
		
//		(nameTextbox.background() <=> {u:University => u.currentEstudent.status })
//		   .setModelToView { (e : EstudenStatus.Status) => e match {  // no debería necesitar el tipo del modelo
//		      case EstudenStatus.EXPELLED => Color.RED
//		      case EstudenStatus.FREE => Color.ORANGE
//		      case EstudenStatus.REGULAR => Color.BLUE
//			}
//		};
		
		// MAP sobre el observable del model (en lugar de sobre el binding)
	    // requiere algebra o combinators sobre Observables
//		(nameTextbox.background() <=> {u:University => u.currentEstudent.status } 
//											.map{ 
//		        								case EstudenStatus.EXPELLED => Color.RED
//		        								case EstudenStatus.FREE => Color.ORANGE
//		        								case EstudenStatus.REGULAR => Color.BLUE
//											}
//
//		)
	
		// otra sintaxis de lo anterior (<= en lugar de .map)
	
//		(nameTextbox.background() <=> {u:University => u.currentEstudent.status } 
//											<= { 
//		        								case EstudenStatus.EXPELLED => Color.RED
//		        								case EstudenStatus.FREE => Color.ORANGE
//		        								case EstudenStatus.REGULAR => Color.BLUE
//											}
//
//		)
	
	

}

