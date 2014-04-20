package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.eclipse.core.databinding.AggregateValidationStatus
import org.uqbar.ui.view.ErrorViewer
import org.eclipse.core.databinding.DataBindingContext
import org.uqbar.lacar.ui.model.WidgetBuilder
import org.eclipse.swt.widgets.Composite

/**
 * Es simplemente una agrupación de diferentes elementos que están presentes en toda ventana y son usados por
 * muchos componentes, empaquetarlos aquí simplemente permite pasar un único parámetro.
 * 
 * De paso está encapsulado y sólo los objetos que realmente usan los elementos aquí provistos son afectados
 * si se agrega un elemento nuevo.
 * 
 * @author npasserini
 */
trait JFaceContainer {
  
  	def getDataBindingContext() : DataBindingContext

	def getErrorViewer() : ErrorViewer

	def status : AggregateValidationStatus

	def getJFaceComposite() : Composite 

	/**
	 * Agrega un hijo al contenedor. Esto es fundamental para que el contenedor al finalizar su construcción
	 * pueda enviar el mensaje {@link WidgetBuilder#pack()} a toda su descendencia.
	 * 
	 * @return Este mismo container, utilidad para anidar mensajes.
	 */
	def addChild(child:WidgetBuilder) : JFaceContainer


}