package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes._
import org.uqbar.lacar.ui.model.BindingBuilder
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.swt.observables.ImageObservableValue
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder

/**
 * Allows to observe the control's image.
 * @author jfernandes
 */
trait WithImageControlBuilder[T <: WidgetWithImage] {
  this : JFaceWidgetBuilder[_ <: WidgetWithImage] =>
  
	def observeImage[M](transformer : Transformer[M, Image] ) : BindingBuilder = {
		new JFaceBindingBuilder(this, new ImageObservableValue[M](getWidget, transformer));
	}

}