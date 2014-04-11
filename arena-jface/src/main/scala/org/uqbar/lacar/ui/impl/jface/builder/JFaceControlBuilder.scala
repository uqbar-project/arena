package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.lacar.ui.model.ControlBuilder
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.SWT
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.swt.observables.ControlObservableValue
import org.eclipse.jface.internal.databinding.swt.SWTProperties
import org.eclipse.swt.widgets.Text
import org.eclipse.jface.databinding.swt.SWTObservables.{observeVisible => observeVis, observeEditable, observeEnabled => observeEnab }
import com.uqbar.commons.collections.Transformer
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.layout.RowData

/**
 * @author npasserini
 * @author jfernandes
 */
abstract class JFaceControlBuilder[T <: Control](container:JFaceContainer)
	extends JFaceWidgetBuilder[T](container) 
	with ControlBuilder {

  var width = SWT.DEFAULT
  var height = SWT.DEFAULT
  
  def this(container:JFaceContainer, jfaceWidget:T) {
		this(container)
		this.initialize(jfaceWidget)
	}
  
	override def observeEnabled() = new JFaceBindingBuilder(this, observeEnabled(getWidget))
	override def observeBackground[M, U](transformer:Transformer[M, U]) = new JFaceBindingBuilder(this, new ControlObservableValue[M, U](getWidget, SWTProperties.BACKGROUND, transformer))
	override def observeVisible() = new JFaceBindingBuilder(this, observeVis(getWidget))

	def observeEnabled(t:T) = if (t.isInstanceOf[Text]) observeEditable(t) else observeEnab(t)

	/**
	 * Utilizad para simplificar la construcción bindings de bajo nivel en forma manual.
	 * 
	 * ATENCIÓN: Esto debe usarse sólo en casos que realmente ameriten la programación a bajo nivel, la forma
	 * preferida de agregar un binding es utilizando los métodos <code>#observeXXX</code> que se encuentran en
	 * las interfaces que dependen de {@link WidgetBuilder} y que devuelven un {@link BindingBuilder} que
	 * permite configurar un binding sin necesidad de escribir código dependiente de la tecnología.
	 * 
	 * @param model
	 * @param view
	 */
	def bind(model:IObservableValue, view:IObservableValue) {
		new JFaceBindingBuilder(this, view, model).build
	}

	override def pack() {
		configureLayoutData
		super.pack
	}

	def configureLayoutData() {
		if (getWidget.getParent.getLayout.isInstanceOf[GridLayout]) {
			val layoutData = new GridData(GridData.FILL)
			layoutData.grabExcessHorizontalSpace = true
			layoutData.widthHint = width
			layoutData.heightHint = height
			getWidget.setLayoutData(layoutData)
		}
		
		if (getWidget.getParent.getLayout.isInstanceOf[RowLayout]){
			getWidget.setLayoutData(new RowData(width, height))
		}
	}

	override def setWidth(preferredSize:Int) { width = preferredSize }
	override def setHeight(preferredSize:Int) { height = preferredSize }
	
	def getControlLayout() : Control = getWidget

}