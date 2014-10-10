package org.uqbar.lacar.ui.impl.jface.builder.windows

import org.eclipse.jface.dialogs.Dialog
import org.eclipse.swt.widgets.Composite

class JFaceDialogBuilder(var parent:JFaceWindowBuilder) extends JFaceWindowBuilder {

	override def createJFaceWindow() = 
		new Dialog(this.parent.window) {
			override def createContents(window:Composite) = createWindowContents(window)
		}

}