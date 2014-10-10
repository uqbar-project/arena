package org.uqbar.lacar.ui.impl.jface.builder.lists

import org.eclipse.swt.widgets.Composite
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.radiogroup.RadioGroup
import org.uqbar.lacar.ui.impl.jface.radiogroup.RadioGroupViewer

class JFaceRadioGroupBuilder[T](container: JFaceContainer) extends JFaceAbstractListBuilder[T, RadioGroupViewer, RadioGroup](container) {

  override def createViewer(jFaceComposite: Composite) = {
    val theViewer = new RadioGroupViewer(jFaceComposite)
    initialize(theViewer.getControl)
    theViewer
  }

}