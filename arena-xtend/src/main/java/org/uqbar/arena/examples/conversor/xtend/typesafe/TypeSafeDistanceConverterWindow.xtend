package org.uqbar.arena.examples.conversor.xtend.typesafe

import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.MainWindow

/**
 * miles -> kilometers converter.
 */
class TypeSafeDistanceConverterWindow extends MainWindow<DistanceConverter> {
	extension ArenaTypeSafeBindingExtensions = new ArenaTypeSafeBindingExtensions
	
	new() {
		super(new DistanceConverter)
	}

	override createContents(Panel mainPanel) {
		title = "Miles To Kilometer Converter"
		mainPanel.layout = new VerticalLayout
		
		"Input in miles".asLabel(mainPanel);
		this.binding[ miles ].asTextBoxIn(mainPanel)
		("Convert" -> [ | this.modelObject.convert ]).asButtonIn(mainPanel)
		this.binding[ kilometers ].asLabelIn(mainPanel)
		" km".asLabel(mainPanel)
		
		// FORMA "VIEJA"

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
	}
	
	def static void main(String[] args) { new TypeSafeDistanceConverterWindow().startApplication }

}