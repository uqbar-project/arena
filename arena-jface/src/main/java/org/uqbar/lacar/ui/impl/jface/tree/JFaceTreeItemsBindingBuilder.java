package org.uqbar.lacar.ui.impl.jface.tree;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.TreeViewer;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ItemsBindingBuilder;

public class JFaceTreeItemsBindingBuilder extends JFaceBindingBuilder implements ItemsBindingBuilder{
	private TreeViewer treeViewer;
	private IObservableSet itemsObservableSet;

	public JFaceTreeItemsBindingBuilder(JFaceTreeBuilder<?> list) {
		super(list, ViewersObservables.observeInput(list.getJFaceTreeViewer()));
		this.treeViewer = list.getJFaceTreeViewer();
	}
	
	public void observeProperty(Object model, String parentPropertyName, String childPropertyName) {
		this.treeViewer.setContentProvider(new ObservableListTreeContentProvider(BeansObservables.listFactory(Realm.getDefault(), childPropertyName, null), null));
		this.treeViewer.setInput(model);
		super.observeProperty(model, parentPropertyName);
		this.itemsObservableSet = JFaceObservableFactory.observeSet(model, childPropertyName);
	}

	@Override
	public <M, V> BindingBuilder adaptItemsUsingProperty(Class<?> modelType, String propertyName) {
		IObservableMap labelProviderMap = BeansObservables.observeMap(itemsObservableSet, modelType, propertyName);
		this.treeViewer.setLabelProvider(new ObservableMapLabelProvider(labelProviderMap));
		return this;
	}
}
