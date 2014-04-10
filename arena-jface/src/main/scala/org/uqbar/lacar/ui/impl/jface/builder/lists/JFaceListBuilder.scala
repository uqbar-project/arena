package org.uqbar.lacar.ui.impl.jface.builder.lists

import org.eclipse.jface.viewers.ListViewer
import org.eclipse.swt.widgets.List
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.SWT._

class JFaceListBuilder[T](container:JFaceContainer) extends JFaceAbstractListBuilder[T, ListViewer, List] (container) {

  override def createViewer(jFaceComposite:Composite) = {
		val viewer = new ListViewer(jFaceComposite, H_SCROLL | V_SCROLL | SINGLE | BORDER | FULL_SELECTION)
		initialize(viewer.getList)
		viewer
	}
  
}