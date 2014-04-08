package org.uqbar.lacar.ui.impl.jface.builder.tree

import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.core.databinding.observable.set.IObservableSet
import org.eclipse.core.databinding.beans.BeansObservables
import org.uqbar.lacar.ui.model.TreeItemsBindingBuilder
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider
import org.eclipse.jface.databinding.viewers.ViewersObservables
import org.eclipse.core.databinding.observable.Realm

class JFaceTreeItemsBindingBuilder(list:JFaceTreeBuilder[_]) 
	extends JFaceBindingBuilder(list, ViewersObservables.observeInput(list.getJFaceTreeViewer)) 
    with TreeItemsBindingBuilder {
  
	private var treeViewer = list.getJFaceTreeViewer 
	private var itemsObservableSet : IObservableSet = _

	def observeProperty(model:Object, parentPropertyName:String, childPropertyName:String) = {
		treeViewer.setContentProvider(new ObservableListTreeContentProvider(BeansObservables.listFactory(Realm.getDefault, childPropertyName, null), null))
		treeViewer.setInput(model)
		super.observeProperty(model, parentPropertyName)
		itemsObservableSet = JFaceObservableFactory.observeSet(model, childPropertyName)
	}

	override def adaptItemsUsingProperty[M,V](modelType:Class[_], propertyName:String) = {
		val labelProviderMap = BeansObservables.observeMap(itemsObservableSet, modelType, propertyName)
		treeViewer.setLabelProvider(new ObservableMapLabelProvider(labelProviderMap))
		this
	}
}