package org.uqbar.arena.aop.potm

import org.uqbar.lacar.ui.model.Action;
import org.uqbar.commons.utils.ReflectionUtils

class Function[A](var method:() => Unit) extends Action {
  override def execute() = method()
}