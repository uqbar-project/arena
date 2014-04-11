package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.arena.widget.traits.Clickable
import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.swt.observables.CaptionObservableValue
import org.uqbar.lacar.ui.model.Action
import org.uqbar.lacar.ui.model.builder.traits.WithCaption
import org.uqbar.lacar.ui.impl.jface.builder.JFaceControlBuilder
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder
import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes._

/**
 * Generic implementation of Clickable interface 
 * that supports any swt component that complies with the SelctionListening
 * type alias.
 * 
 * @author jfernandes
 */
trait JFaceClickable extends Clickable {
  this : JFaceControlBuilder[_ <: SelectionListening] =>
	
  override def onClick(action: Action) : this.type = {
    getWidget addSelectionListener(new JFaceActionAdapter(getContainer, action))
    this
  }
}

trait JFaceWithCaption extends WithCaption {
  this : JFaceWidgetBuilder[_ <: WithText] =>
    
  override def observeCaption() = new JFaceBindingBuilder(this, new CaptionObservableValue(getWidget))
  
  def setCaption(caption: String) : this.type = {
    getWidget setText caption
    this
  }
}