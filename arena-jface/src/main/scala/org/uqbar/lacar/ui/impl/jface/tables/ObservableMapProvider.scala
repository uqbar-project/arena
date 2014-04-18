package org.uqbar.lacar.ui.impl.jface.tables

import org.eclipse.core.databinding.observable.map.IMapChangeListener
import org.eclipse.core.databinding.observable.map.IObservableMap
import org.eclipse.core.databinding.observable.map.MapChangeEvent
import org.eclipse.jface.viewers.ColumnLabelProvider
import org.eclipse.jface.viewers.ITableLabelProvider
import org.eclipse.jface.viewers.LabelProviderChangedEvent
import org.eclipse.swt.graphics.Image
import scala.actors.threadpool.Arrays
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.eclipse.jface.viewers.ITableColorProvider
import com.uqbar.commons.collections.Transformer
import java.awt.Color
import org.eclipse.swt.widgets.Widget
import org.uqbar.lacar.ui.impl.jface.bindings.JavaBeanTransacionalObservableMap
import org.uqbar.ui.swt.utils.SWTUtils

/**
 * @since 1.1
 * 
 */
class ObservableMapProvider extends ColumnLabelProvider with ITableLabelProvider with ITableColorProvider{
	var attributeLabelMaps = List[IObservableMap]()
	var attributeBackgroudMaps =  Map[Integer, JavaBeanTransacionalObservableMap]()
	var backgroundTransformers = Map[Integer, Transformer[Any, _]]()
	var widget:Widget=_

	val mapChangeListener = new IMapChangeListener() {
		def handleMapChange(event:MapChangeEvent) {
			val affectedElements = event.diff.getChangedKeys()
			val newEvent = new LabelProviderChangedEvent(ObservableMapProvider.this, affectedElements.toArray())
			fireLabelProviderChanged(newEvent)
		}
	};
	

	def this(attributeMaps:Array[IObservableMap]) {
	    this()
		attributeLabelMaps = attributeMaps.toList
		attributeLabelMaps.foreach(_.addMapChangeListener(mapChangeListener))
	}
	
	def this(attributeMap:IObservableMap) {
		this(Array(attributeMap))
	}
	
	def initializeBackground(maps:java.util.Map[Integer, Transformer[Any, _]], backgroundMaps:java.util.Map[Integer, JavaBeanTransacionalObservableMap] ){
	  backgroundTransformers = maps.toMap
	  attributeBackgroudMaps = backgroundMaps.toMap
	  attributeBackgroudMaps.values.foreach(_.addMapChangeListener(mapChangeListener))
	}
	
	override def getBackground(element:Object):org.eclipse.swt.graphics.Color =  getBackground(element, 0)

	override def dispose() {
	  attributeLabelMaps.foreach(_.removeMapChangeListener(mapChangeListener))
	  super.dispose()
	}

	override def getText(element:Object)  = getColumnText(element, 0)
	
	def getColumnImage(element:Object, columnIndex:Int):Image = null

	def getColumnText(element:Object, columnIndex:Int):String = {
		if (columnIndex < attributeLabelMaps.length) {
			val result = attributeLabelMaps(columnIndex).get(element)
			return if (result == null)  "" else  result.toString
		}
		return null;
	}
	
	def getForeground(element:Object, columnIndex:Int) : org.eclipse.swt.graphics.Color = null
	
	def getBackground(element:Object, columnIndex:Int) : org.eclipse.swt.graphics.Color = {
		if (backgroundTransformers.contains(columnIndex)) {
			val modelValue = attributeBackgroudMaps(columnIndex).getValue(element)
			if (modelValue != null) {
				val color = backgroundTransformers(columnIndex).transform(modelValue).asInstanceOf[Color]
				return SWTUtils.getSWTColor(widget.getDisplay(), color)
			}
		}
		null
	}

}