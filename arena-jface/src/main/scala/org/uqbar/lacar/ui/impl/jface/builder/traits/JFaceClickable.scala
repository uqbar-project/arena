package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder
import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter
import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes.SelectionListening
import org.uqbar.lacar.ui.model.Action
import org.uqbar.lacar.ui.model.builder.traits.Clickeable
import org.uqbar.arena.widget.traits.Clickable

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
    getWidget.addSelectionListener(new JFaceActionAdapter(getContainer, action))
    this
  }

}