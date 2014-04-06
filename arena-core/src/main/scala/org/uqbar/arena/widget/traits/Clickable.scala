package org.uqbar.arena.widget.traits

import org.uqbar.lacar.ui.model.Action

trait Clickable {

  def onClick(action : Action) : this.type
  
}