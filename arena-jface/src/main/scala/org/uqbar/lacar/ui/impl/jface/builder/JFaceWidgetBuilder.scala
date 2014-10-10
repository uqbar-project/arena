package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.swt.widgets.Widget
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer

abstract class JFaceWidgetBuilder[T <: Widget](var container:JFaceContainer) extends AbstractWidgetBuilder {
	container.addChild(this)
	var widget : T = _
	
	def this(container:JFaceContainer, widget:T) {
	  this(container)
	  this.initialize(widget)
	}
	
	protected def initialize(jFaceWidget:T) {
		widget = jFaceWidget;
	}
	
	def getDataBindingContext() = container.getDataBindingContext

	protected def getDescription() = getClass.getSimpleName
	
}