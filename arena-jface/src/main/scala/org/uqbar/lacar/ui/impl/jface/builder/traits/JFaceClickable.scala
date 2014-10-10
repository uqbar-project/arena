package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.swt.observables.CaptionObservableValue
import org.uqbar.lacar.ui.model.Action
import org.uqbar.lacar.ui.model.builder.traits.WithCaption
import org.uqbar.lacar.ui.impl.jface.builder.JFaceControlBuilder
import org.uqbar.lacar.ui.impl.jface.builder.JFaceWidgetBuilder
import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes._
import org.uqbar.lacar.ui.model.builder.traits.Clickable

/**
 * Generic implementation of Clickable interface 
 * that supports any swt component that complies with the SelctionListening
 * type alias.
 * 
 * @author jfernandes
 */
trait JFaceClickable extends Clickable {
  this : JFaceWidgetBuilder[_ <: SelectionListening] =>
	
  override def onClick(action: Action) : this.type = {
    widget addSelectionListener(new JFaceActionAdapter(container, action))
    this
  }
}

trait JFaceWithCaption extends WithCaption {
  this : JFaceWidgetBuilder[_ <: WithText] =>
    
  override def observeCaption() = new JFaceBindingBuilder(this, new CaptionObservableValue(widget))
  
  def setCaption(caption: String) : this.type = {
    widget setText caption
    this
  }
}