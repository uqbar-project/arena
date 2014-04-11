package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.lacar.ui.impl.jface.swt.observables.FileSelectorObservableValue
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.model.Action
import org.uqbar.arena.scala.ArenaScalaImplicits._

class JFaceFileSelectorBuilder(context: JFaceContainer, caption: String, title: String, path: String, extensions: Array[String])
  extends JFaceButtonBuilder(context) {

  setCaption(caption)
  onClick(openFileAction())
  var value = new FileSelectorObservableValue(getWidget(), title, path, extensions);

  override def observeValue() = new JFaceBindingBuilder(this, this.value)

  def openFileAction(): Action = () => value.openFile()

}