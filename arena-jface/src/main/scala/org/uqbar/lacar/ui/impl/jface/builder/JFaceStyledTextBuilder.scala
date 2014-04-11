package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.swt.widgets.KeyWordText
import org.uqbar.arena.widgets.style.Style
import org.uqbar.lacar.ui.model.BindingBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.swt.SWT
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.bindings.observables.KeyWordTextObservableValue

/**
 * Created by jfernandes on 4/8/14.
 */
class JFaceStyledTextBuilder(container: JFaceContainer, configuration: java.util.Map[Array[String], Style])
  extends JFaceSkinnableControlBuilder[KeyWordText](container, new KeyWordText(container.getJFaceComposite, configuration)) {

  override def observeValue = {
    new JFaceBindingBuilder(this, new KeyWordTextObservableValue(widget, SWT Modify))
  }

}
