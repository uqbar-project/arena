package org.uqbar.lacar.ui.impl.jface.builder.windows

import scala.collection.JavaConversions._
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.model.WindowBuilder
import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.jface.window.Window
import org.eclipse.core.databinding.AggregateValidationStatus
import org.uqbar.ui.view.ErrorViewer
import org.uqbar.lacar.ui.model.WidgetBuilder
import com.uqbar.commons.collections.CollectionFactory
import org.uqbar.lacar.ui.model.ViewDescriptor
import org.uqbar.lacar.ui.model.PanelBuilder
import org.apache.commons.lang.StringUtils
import org.eclipse.swt.widgets.Composite
import org.uqbar.lacar.ui.impl.jface.builder.JFacePanelBuilder
import org.uqbar.arena.windows.MessageBox._
import org.eclipse.swt.SWT
import com.uqbar.commons.exceptions.ProgramException
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Listener
import org.eclipse.swt.widgets.Event
import org.uqbar.arena.jface.JFaceImplicits._

//TODO SCALA: 
//  - se puede usar los lazy para crear los atributos la primera vez?

class JFaceWindowBuilder extends AbstractWidgetBuilder with WindowBuilder with JFaceContainer {
  var dbc = new DataBindingContext()
  lazy val window : Window = createJFaceWindow
  var children : java.util.List[WidgetBuilder] = CollectionFactory.createList()
  var windowDescriptor : ViewDescriptor[PanelBuilder] = _
	// TODO Para no obligar a definir un ErrorViewer, podríamos tener uno
	// default que tira los errores por
	// consola o algo así.
	var errorViewer:ErrorViewer = _
	var title:String = _
	var status : AggregateValidationStatus = _
	var iconImage:String = _

	override def setTitle(title:String) {
		this.title = title;
	}

	override def setContents(windowDescriptor:ViewDescriptor[PanelBuilder]) {
		this.windowDescriptor = windowDescriptor;
	}
	
	override def setIcon(iconImage:String) {
		this.iconImage = iconImage;
	}

	override def open() {
		// Esto crea tanto la ventana como sus contenidos (termina llamando a createWindowContents).
		window.create();

		// Esto debe hacerse después del create, en caso contrario no hay shell
		// todavía.
		window.getShell.setText(title)
		window.getShell.pack
		if (StringUtils.isNotEmpty(iconImage)) {
			window.getShell.setImage(new Image(window.getShell.getDisplay, iconImage))
		}

		// Una configuración adicional.
		window.setBlockOnOpen(true)

		window.getShell().addListener(SWT.Close, (event:Event) => windowDescriptor close)

		// Al hacer open se podría evitar el create anterior, pero necesito
		// hacerlo para poder hacer getShell
		// entre ambos.
		window open
	}

	override def pack() {
		super.pack
		children.foreach(_ pack)
	}
	
	def createWindowContents(window:Composite) = {
		val builder = new JFacePanelBuilder(this)
		// TODO Está hardcodeado el layout del panel principal de la ventana.
		builder setVerticalLayout;
		windowDescriptor showOn builder
		pack
		builder.getWidget()
	}
	
	override def getDataBindingContext() = dbc
	
	override def createWindow() = new JFaceDialogBuilder(this)
	
	override def addChild(child:WidgetBuilder) = {
		this.children.add(child)
		this
	}
	
	override def showMessage(msgType:Type, message:String) {
		val messageBox = new org.eclipse.swt.widgets.MessageBox(getShell, SWT.OK | computeStyle(msgType));
		messageBox.setMessage(message)
		messageBox.setText(getShell getText)
		messageBox.open
	}
	
	def computeStyle(msgType:Type) = {
		msgType match {
			case Type.Information => 	SWT.ICON_INFORMATION
			case Type.Warning => 		SWT.ICON_WARNING
			case Type.Error =>			SWT.ICON_ERROR
			case _ =>			throw new UnsupportedOperationException("Invalid message box style: " + msgType)
		}
	}
	
	override def close() {
		if (getShell != null) {
			getShell close
		}
	}
	
	// manejo de errores
	
	override def getStatus() = {
		if (status == null) {
			status = new AggregateValidationStatus(dbc, AggregateValidationStatus.MAX_SEVERITY)
		}
		status;
	}
	
	override def getErrorViewer() = {
		if (errorViewer == null) {
			throw new ProgramException("Esta ventana no tiene capacidad de mostrar errores por no habérsele configurado un ErrorViewer");
		}
		errorViewer;
	}
	
	override def setErrorViewer(errorViewer:ErrorViewer) {
		this.errorViewer = errorViewer;
	}

	override def getJFaceComposite() = getShell
	
	// WARNING: Este método falla si no se invocó el #create primero.
	def getShell() = window getShell
	
	def createJFaceWindow() : Window = {
		new ApplicationWindow(null) {
			override def createContents(window:Composite) = {
				createWindowContents(window)
			}
		}
	}

}