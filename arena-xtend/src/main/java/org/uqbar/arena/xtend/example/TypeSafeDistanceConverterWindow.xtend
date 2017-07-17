package org.uqbar.arena.xtend.example

import java.awt.Color
import org.uqbar.arena.bindings.ValueTransformer
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.MainWindow

import static extension org.uqbar.arena.xtend.ArenaXtendExtensions.*

/**
 * miles to kilometers converter.
 */
class TypeSafeDistanceConverterWindow extends MainWindow<DistanceConverter> {
//	extension ArenaXTendFactory = new ArenaXTendFactory
	
	new() {
		super(new DistanceConverter)
	}

	override createContents(Panel mainPanel) {
		title = "Miles To Kilometer Converter"
		mainPanel.layout = new VerticalLayout

//      FORMA "NUEVA"		
//		"Input in miles".asLabel(mainPanel)
//		binding[ miles ].asTextBoxIn(mainPanel)
//		
//		("Convert" -> [ | this.modelObject.convert ] as Procedure0).asButtonIn(mainPanel)
//		
//		binding[ kilometers ].asLabelIn(mainPanel)
//		
//		" km".asLabel(mainPanel)
		
		// FORMA "VIEJA" CON TYPE SAFE BINDINGS
//		new Label(mainPanel) => [
//			text = "Input in miles"
//		]
//		new TextBox(mainPanel) => [
//			bindValue([ DistanceConverter c | c.miles ])
//		]
//
//		new Button(mainPanel) => [
//			caption = "Convert"
//			onClick [ | this.modelObject.convert ]
//		]
//
//		new Label(mainPanel) => [
//			background = Color.ORANGE
//			bindValue([DistanceConverter c | c.kilometers])
//		]
//		new Label(mainPanel) => [
//			text = " km"
//		]

		new Label(mainPanel) => [
			text = "Miles"
		]
		
		new TextBox(mainPanel) => [
			value <=> "miles"
			(background <=> "miles").transformer = new MilesTransformer;
			(enabled <=> "miles").transformer = new MilesEnabledTransformer
		]
		
		new Button(mainPanel) => [
			caption = "Convert"
			onClick [ | this.modelObject.convert ]
			// Testing #32 - https://github.com/uqbar-project/arena/issues/32
			// enabled <=> [ DistanceConverter c | c.miles > 20 ]
			// enabled <=> [ DistanceConverter c | c.conversionEnabled ]
		]

		new Label(mainPanel) => [
			value <=> "kilometers"	
		]


	}
	
	def static void main(String[] args) { 
		new TypeSafeDistanceConverterWindow().startApplication
	}
	
}

class MilesTransformer implements ValueTransformer<Double, Color> {
	
	override getModelType() {
		typeof(Double)
	}
	
	override getViewType() {
		typeof(Color)
	}
	
	override modelToView(Double valueFromModel) {
		if (valueFromModel > 100) Color.BLUE else Color.GREEN 
	}
 	
	override viewToModel(Color valueFromView) {
		null
	}
	
}

class MilesEnabledTransformer implements ValueTransformer<Double, Boolean> {
	
	override getModelType() {
		typeof(Double)
	}
	
	override getViewType() {
		typeof(Boolean)
	}
	
	override modelToView(Double valueFromModel) {
		valueFromModel > -2 
	}
 	
	override viewToModel(Boolean valueFromView) {
		null
	}
	
}
