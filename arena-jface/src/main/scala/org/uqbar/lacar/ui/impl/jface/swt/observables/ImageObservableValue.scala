package org.uqbar.lacar.ui.impl.jface.swt.observables

import org.eclipse.core.databinding.observable.Diffs
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue
import org.eclipse.jface.resource.ImageDescriptor
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes.WidgetWithImage

import com.uqbar.commons.collections.Transformer
/**
 * @author jfernandes
 */
class ImageObservableValue[T](w : WidgetWithImage, var transformer : Transformer[T, Image] ) extends AbstractSWTObservableValue(w) {
  
  override def getWidget() : WidgetWithImage = { super.getWidget().asInstanceOf[WidgetWithImage] }
  
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