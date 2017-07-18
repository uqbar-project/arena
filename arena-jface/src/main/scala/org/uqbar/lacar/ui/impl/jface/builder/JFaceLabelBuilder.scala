package org.uqbar.lacar.ui.impl.jface.builder

import org.eclipse.jface.databinding.swt.SWTObservables.observeText
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Label
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.WithImageBuilder
import org.uqbar.lacar.ui.model.LabelBuilder
import org.eclipse.swt.events.ControlListener
import org.eclipse.core.databinding.observable.Realm
import org.uqbar.arena.widgets.traits.WidgetWithAlignment

class JFaceLabelBuilder(container:JFaceContainer, label: Label) 
	extends JFaceSkinnableControlBuilder[Label](container, label)
	with LabelBuilder
	with WidgetWithAlignment
	with WithImageBuilder[Label] {
  
  def this(container: JFaceContainer) = {
    this(container,new Label(container.getJFaceComposite, SWT.CENTER))
  }
  
  override def setText(text:String) = widget.setText(text)
  
  // Esto no parece tener sentido. Si el label es readonly para qué observar o bindear ? 
  override def observeValue() = new JFaceBindingBuilder(this, observeText(widget))

  override def alignLeft() = label.setAlignment(SWT.LEFT)
  override def alignRight() = label.setAlignment(SWT.RIGHT)
  override def alignCenter() = label.setAlignment(SWT.CENTER)
  
}

class JFaceErrorsPanelBuilder(container:JFaceContainer, label: Label) extends JFaceLabelBuilder(container, label) {

  def this(container: JFaceContainer, label: Label, preferredLines: Int) {
      this(container, label)
      this.height = 22 * preferredLines
      this.width = SWT.DEFAULT
  }

}