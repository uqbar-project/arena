package org.uqbar.arena.scala

import org.uqbar.lacar.ui.model.Action
import scala.collection.mutable.ArrayBuffer

/**
 * @author jfernandes
 */
class CompositeAction() extends Action {
  var actions = new ArrayBuffer[Action]

  def this(a: Action, b: Action) = {
    this()
    actions += a
    actions += b
  }

  override def execute() = {
    actions.foreach(_ execute)
  }

}