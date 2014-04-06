package org.uqbar.arena.bindings.observables

import org.uqbar.lacar.ui.model.bindings.ViewObservable
import org.uqbar.lacar.ui.model.ButtonBuilder
import org.uqbar.lacar.ui.model.BindingBuilder
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.model.WithImageBuilder

object ViewObservables {
  
  def observableImage[M](transformer : Transformer[M, Image]) : ViewObservable[WithImageBuilder] = {
    new ViewObservable[WithImageBuilder] {
      override def createBinding(control : WithImageBuilder) = {
        control.observeImage(transformer)
      }
    }
  }

}