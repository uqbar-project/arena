package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes._
import org.uqbar.lacar.ui.model.BindingBuilder
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.builder.JFaceWidgetBuilder
import org.eclipse.jface.databinding.swt.SWTObservables

/**
 * Allows to observe the control's image.
 * @author jfernandes
 */
trait WithImageBuilder[T <: WidgetWithImage] {
  this : JFaceWidgetBuilder[_ <: WidgetWithImage] =>
  
	def observeImage[M](transformer : Transformer[M, Image] ) =
		new JFaceBindingBuilder(WithImageBuilder.this, 
//        new ImageObservableValue[M](widget, transformer)
        //TODO: add transformer
        SWTObservables.observeImage(widget)
    )

}