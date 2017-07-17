package org.uqbar.arena.aop.potm

import org.uqbar.lacar.ui.model.Action;

class Function[A](var method:() => Unit) extends Action {
  override def execute() = method()
}