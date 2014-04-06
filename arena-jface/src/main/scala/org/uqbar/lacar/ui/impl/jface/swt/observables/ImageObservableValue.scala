package org.uqbar.lacar.ui.impl.jface.swt.observables

import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue
import org.eclipse.core.databinding.observable.value.IValueChangeListener
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.swt.widgets.Widget
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Label
import org.eclipse.core.databinding.observable.Diffs
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Text

object ImageObservableValue {
  type WidgetWithImage = Widget { 
    def getImage() : org.eclipse.swt.graphics.Image 
  	def setImage(image:org.eclipse.swt.graphics.Image) : Unit
  }
}

/**
 * @author jfernandes
 */
class ImageObservableValue[T](w : ImageObservableValue.WidgetWithImage, var transformer : Transformer[T, Image] ) extends AbstractSWTObservableValue(w) {
  
  override def getWidget() : ImageObservableValue.WidgetWithImage = { super.getWidget().asInstanceOf[ImageObservableValue.WidgetWithImage] }
  
  override def doGetValue() = { getWidget.getImage }
  
  override def doSetValue(value:Object) = {
    val oldValue = doGetValue
    val newValue = transformer.transform(value.asInstanceOf[T])

    getWidget.setImage(newValue)
    
    if (!newValue.equals(oldValue)) {
    	fireValueChange(Diffs.createValueDiff(oldValue, newValue));
	}
  }
  
  override def getValueType = classOf[Image]
  
  implicit def arenaToSwtImage(image:Image) : org.eclipse.swt.graphics.Image = {
    // Esto seguro que no está bien. En SWT hay que llevar un registry de image,
    // y tratar de trabajar con ImageDescriptors que son mas livianos.
    val urlToImage = getClass.getClassLoader().getResource(image.getPathToFile())
	ImageDescriptor.createFromURL(urlToImage).createImage()
  }
  
}