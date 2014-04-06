package org.uqbar.arena.jface

import org.eclipse.core.databinding.observable.value.ComputedValue

/**
 * @author jfernandes
 */
object JFaceImplicits {
  
  implicit def closureToComputedValue(closure : () => Object) : ComputedValue = { 
    new ComputedValue() {
	  override def calculate() : Object = closure()
    }
  }
}

