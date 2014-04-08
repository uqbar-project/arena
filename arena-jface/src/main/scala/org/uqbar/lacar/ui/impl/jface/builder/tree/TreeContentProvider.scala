package org.uqbar.lacar.ui.impl.jface.builder.tree

import org.eclipse.jface.viewers.ITreeContentProvider
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.core.databinding.observable.set.IObservableSet
import org.uqbar.commons.utils.ReflectionUtils._
import java.util.List
import org.eclipse.jface.viewers.Viewer
import org.uqbar.arena.jface.JFaceImplicits._
import org.eclipse.jface.databinding.swt.SWTObservables._
import org.eclipse.core.databinding.beans.BeansObservables
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory._

/**
 * 
 */
class TreeContentProvider(parentPropertyName:String, childrenPropertyName:String) extends ITreeContentProvider {
	private var observableParentValue : IObservableValue = _
	private var observableChildrenValue : IObservableSet = _

	//TODO: Esto no se banca colecciones de scala
	override def getChildren(parentElement:Object) = { 
		invokeGetter(parentElement, childrenPropertyName).asInstanceOf[List[_]].toArray
	}

	override def getParent(element:Object) = invokeGetter(element, parentPropertyName)
	override def getElements(inputElement:Object) = getChildren(inputElement)
	override def hasChildren(element:Object) = true

	override def dispose() = {
		disposeParentValue
		if (observableChildrenValue != null) observableChildrenValue dispose
	}

	def disposeParentValue() = if (observableParentValue != null) observableParentValue dispose

	override def inputChanged(viewer:Viewer, oldInput:Object, newInput:Object) = {
		// Dispose old value observer
		disposeParentValue

		// Create new observer and listen to its changes.
		if (newInput != null) {
			observableChildrenValue = observeSet(newInput, childrenPropertyName)
			observableParentValue = observeProperty(newInput, parentPropertyName)
			observableParentValue.addValueChangeListener{() => viewer.refresh }
		}
		else {
			this.observableChildrenValue = null
		}
	}

}