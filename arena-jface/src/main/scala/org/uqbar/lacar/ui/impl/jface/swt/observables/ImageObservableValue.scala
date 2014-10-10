package org.uqbar.lacar.ui.impl.jface.swt.observables

import org.eclipse.core.databinding.observable.Diffs
import org.eclipse.jface.resource.ImageDescriptor
import org.uqbar.arena.graphics.Image
import org.uqbar.lacar.ui.impl.jface.swt.SwtTypes.WidgetWithImage
import org.uqbar.arena.jface.JFaceImplicits._
import com.uqbar.commons.collections.Transformer
import org.eclipse.jface.resource.ImageRegistry
/**
 * @author jfernandes
 */
//class ImageObservableValue[T](w : WidgetWithImage, var transformer : Transformer[T, Image] ) extends AbstractSWTObservableValue(w) {
//  
//  w.addDisposeListener( disposeImage _)
//  
//  override def getWidget() : WidgetWithImage = { super.getWidget().asInstanceOf[WidgetWithImage] }
//  
//  override def doGetValue() = { getWidget.getImage }
//  
//  override def doSetValue(value:Object) = {
//    val oldValue = doGetValue
//    val newValue = transformer.transform(value.asInstanceOf[T])
//
//    getWidget.setImage(newValue)
//    
//    if (!newValue.equals(oldValue)) {
//    	fireValueChange(Diffs.createValueDiff(oldValue, newValue));
//	}
//  }
//  
//  override def getValueType = classOf[Image]
//  
//  implicit def arenaToSwtImage(image:Image) : org.eclipse.swt.graphics.Image = {
//    ImageObservableValue.getImage(image)
//  }
//  
//  def disposeImage() {
//  }
//  
//}
//
//object ImageObservableValue {
//  var registry = new ImageRegistry();
//  
//  def getImage(img:Image) = {
//    if (registry.get(img.pathToFile) == null) {
//    	registry.put(img.pathToFile, createImage(img))
//    }
//    registry.get(img.pathToFile)
//  }
//  
//  def createImage(img:Image) = {
//    val urlToImage = getClass.getClassLoader().getResource(img.pathToFile)
//    var data = ImageDescriptor.createFromURL(urlToImage).getImageData()
//    if (img.scale != null)
//    	data = data.scaledTo(img.scale._1, img.scale._2)
//    ImageDescriptor.createFromImageData(data).createImage()
//  }
//  
//}