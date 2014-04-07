package org.uqbar.lacar.ui.impl.jface.builder

import org.uqbar.lacar.ui.model.ControlBuilder

import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes._
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder
import org.uqbar.lacar.ui.model.BindingBuilder
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.swt.observables.ImageObservableValue
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder

/**
 * Allows to observe the control's image.
 * 
 * @author jfernandes
 */
trait WithImageControlBuilder[T <: WidgetWithImage] extends JFaceWidgetBuilder[T] {
  
	def observeImage[M](transformer : Transformer[M, Image] ) : BindingBuilder = {
		new JFaceBindingBuilder(this, new ImageObservableValue[M](getWidget, transformer));
	}

}