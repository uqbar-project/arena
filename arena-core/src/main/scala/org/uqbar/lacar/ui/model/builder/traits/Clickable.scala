package org.uqbar.lacar.ui.model.builder.traits

import org.uqbar.lacar.ui.model.Action

/**
 * @author jfernandes
 */
trait Clickable {

  def onClick(action : Action) : this.type
  
}