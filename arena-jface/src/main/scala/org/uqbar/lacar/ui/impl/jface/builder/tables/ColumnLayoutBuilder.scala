package org.uqbar.lacar.ui.impl.jface.builder.tables

import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableLayoutBuilder
import org.uqbar.arena.widgets.tables.ColumnLayoutBuilder

class WeightColumnLayoutBuilder(var weight:Int) extends ColumnLayoutBuilder[JFaceTableLayoutBuilder] {
	override def configure(tableLayoutBuilder:JFaceTableLayoutBuilder) = {
		tableLayoutBuilder addColumnWithWieght(weight)
	}
}

class FixedColumnLayoutBuilder(var pixels:Int) extends ColumnLayoutBuilder[JFaceTableLayoutBuilder] {
	override def configure(tableLayoutBuilder:JFaceTableLayoutBuilder) = {
		tableLayoutBuilder addFixedColumn(pixels)
	}
}

class DefaultColumnLayoutBuilder extends ColumnLayoutBuilder[JFaceTableLayoutBuilder] {
	override def configure(tableLayoutBuilder:JFaceTableLayoutBuilder) = {
		tableLayoutBuilder addColumnWithDefaultWeight
	}
}


