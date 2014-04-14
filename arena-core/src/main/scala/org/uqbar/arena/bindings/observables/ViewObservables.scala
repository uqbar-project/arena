package org.uqbar.arena.bindings.observables

import org.uqbar.lacar.ui.model.bindings.ViewObservable
import org.uqbar.lacar.ui.model.ButtonBuilder
import org.uqbar.lacar.ui.model.BindingBuilder
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.model.builder.traits.WithImageBuilder
import org.uqbar.lacar.ui.model.WidgetBuilder

object ViewObservables {
  
  type BuilderWithImage = WidgetBuilder with WithImageBuilder
  
  def observableImage[M](transformer : Transformer[M, Image]) : ViewObservable[BuilderWithImage] = {
    new ViewObservable[BuilderWithImage] {
      override def createBinding(control : BuilderWithImage) = {
        control.observeImage(transformer)
      }
    }
  }

}