package org.uqbar.arena.bindings.observables

import org.uqbar.lacar.ui.model.bindings.ViewObservable
import org.uqbar.lacar.ui.model.ButtonBuilder
import org.uqbar.lacar.ui.model.BindingBuilder
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.model.builder.traits.WithImageBuilder
import org.uqbar.lacar.ui.model.WidgetBuilder
import org.uqbar.arena.widgets.Widget
import org.uqbar.lacar.ui.model.bindings.AbstractViewObservable

object ViewObservables {
  
  type BuilderWithImage = WidgetBuilder with WithImageBuilder
  
  def observableImage[W <: Widget, M](widget : W, transformer : Transformer[M, Image]) : ViewObservable[W,BuilderWithImage] = {
    new AbstractViewObservable[W,BuilderWithImage](widget) {
      override def createBinding(control : BuilderWithImage) = {
        control.observeImage(transformer)
      }
    }
  }

}