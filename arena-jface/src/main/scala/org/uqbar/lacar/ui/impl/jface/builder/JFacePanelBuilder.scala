package org.uqbar.lacar.ui.impl.jface.builder

import scala.collection.mutable.ArrayBuffer

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.RowData
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Layout
import org.uqbar.arena.widgets.style.Style
import org.uqbar.arena.widgets.tree.Tree
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.JFaceFileSelectorBuilder
import org.uqbar.lacar.ui.impl.jface.JFaceSelectorBuilder
import org.uqbar.lacar.ui.impl.jface.JFaceStyledTextBuilder
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.ObservableErrorPanelForegroundColor
import org.uqbar.lacar.ui.impl.jface.bindings.ObservableStatusMessage
import org.uqbar.lacar.ui.impl.jface.builder.traits.Layoutable
import org.uqbar.lacar.ui.impl.jface.builder.tree.JFaceTreeBuilder
import org.uqbar.lacar.ui.impl.jface.lists.JFaceListBuilder
import org.uqbar.lacar.ui.impl.jface.radiogroup.JFaceRadioGroupBuilder
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableBuilder
import org.uqbar.lacar.ui.model.Action
import org.uqbar.lacar.ui.model.ListBuilder
import org.uqbar.lacar.ui.model.PanelBuilder
import org.uqbar.lacar.ui.model.TableBuilder
import org.uqbar.lacar.ui.model.WidgetBuilder

/**
 * @author jfernandes
 */
class JFacePanelBuilder(container:JFaceContainer, composite:Composite) 
	extends JFaceWidgetBuilder[Composite](container, composite)
	with PanelBuilder 
	with JFaceContainer 
	with Layoutable {
  private var children = new ArrayBuffer[WidgetBuilder]
  
  def this(container:JFaceContainer) = 
		this(container, new Composite(container.getJFaceComposite(), SWT.NONE))

  	// ********************************************************
	// ** Components
	// ********************************************************

	override def addLabel() = new JFaceLabelBuilder(this)
	override def addTextBox(multiLine: Boolean) = new JFaceTextBuilder(this, multiLine)
	override def addStyleTextArea(configurationStyle : java.util.Map[Array[String], Style]) = new JFaceStyledTextBuilder(this, configurationStyle)
	override def addSpinner(minValue:Integer, maxValue:Integer) = new JFaceSpinnerBuilder(this, minValue, maxValue)

	override def addCheckBox() = new JFaceCheckBoxBuilder(new Button(getWidget, SWT.CHECK), this)

	override def addButton(caption:String, action:Action) = new JFaceButtonBuilder(this).setCaption(caption).onClick(action)
	override def addLink(caption:String, action:Action) = new JFaceLinkBuilder(this).setCaption(caption).onClick(action)
	override def addFileButton(caption:String, title:String, path:String, extensions:Array[String]) = new JFaceFileSelectorBuilder(this, caption, title, path, extensions)

	override def addTable[R](itemType : Class[R]) : TableBuilder[R] = new JFaceTableBuilder[R](this, itemType)
	
	override def addTree[T](tree:Tree[T], propertyNode:String) = new JFaceTreeBuilder[T](this, tree, propertyNode)

	override def addSelector[T](nullValue : Boolean) : ListBuilder[T] =  new JFaceSelectorBuilder[T](this)
	override def addList[T](): ListBuilder[T] = new JFaceListBuilder[T](this)
	
	override def addRadioSelector[T]():ListBuilder[T] = new JFaceRadioGroupBuilder[T](this)

	// ********************************************************
	// ** Panel
	// ********************************************************

	override def addChildPanel() = new JFacePanelBuilder(this)
	
	override def addChildGroup(title:String) = {
		val group = new Group(getJFaceComposite, SWT.SHADOW_IN)
		group.setText(title)
		new JFacePanelBuilder(this, group)
	}

	override def addErrorPanel(okMessage:String) = {
		// TODO Usar el framework para configurar el label en lugar de hacerlo manualmente.
		val errorLabel = new Label(getWidget, SWT.WRAP)
		errorLabel.setLayoutData(new RowData(250, 50))

		// fija el background del label. por default es blanco, al igual que el de eclipse
		errorLabel.setBackground(getWidget.getDisplay.getSystemColor(SWT.COLOR_WHITE))

		val labelBuilder = new JFaceLabelBuilder(this, errorLabel)
		labelBuilder.bind(//
			new ObservableStatusMessage(getStatus, okMessage), //
			SWTObservables.observeText(errorLabel))

		labelBuilder.bind(//
			SWTObservables.observeForeground(errorLabel),//
			new ObservableErrorPanelForegroundColor(getStatus))
	}

	protected def setLayout(layout:Layout) :Unit = getWidget setLayout layout


	override def setPreferredWidth(width:Int) = {
		// TODO Hacer una abstracción más genérica que permita manejar distintos tipos de Layout Data.
		// this.getWidget().setLayoutData(new RowData(width , 500))
	}

	// ********************************************************
	// ** JFaceContainer
	// ********************************************************

	override def getErrorViewer() = getContainer getErrorViewer
	override def getStatus() = getContainer getStatus
	override def getJFaceComposite() = getWidget

	override def pack() = children foreach(_ pack)
	
	override def addChild(child: WidgetBuilder) = {
		children += child
		this
	}
  
}