package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.lacar.ui.impl.jface.lists.JFaceAbstractListBuilder
import org.eclipse.swt.widgets.Combo
import org.eclipse.jface.viewers.ComboViewer
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite

class JFaceSelectorBuilder[T](container: JFaceContainer)
  extends JFaceAbstractListBuilder[T, ComboViewer, Combo](container) {

  override def createViewer(jFaceComposite: Composite) = {
    val viewer = new ComboViewer(jFaceComposite, SWT.LEFT | SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
    this.initialize(viewer.getCombo)
    viewer
  }

}