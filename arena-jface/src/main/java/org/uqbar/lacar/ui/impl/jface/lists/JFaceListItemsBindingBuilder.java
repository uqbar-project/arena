package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory;
import org.uqbar.lacar.ui.impl.jface.builder.lists.JFaceAbstractListBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ItemsBindingBuilder;

/**
 * Creates bindings for the items of list viewers, such as {@link ListViewer} or {@link ComboViewer}
 * 
 * @author npasserini
 */
public class JFaceListItemsBindingBuilder extends JFaceItemsBindingBuilder implements ItemsBindingBuilder {
	private IObservableSet itemsObservableSet;

	public JFaceListItemsBindingBuilder(JFaceAbstractListBuilder<?, ?, ?> list) {
		super(list.viewer());
	}

	@Override
	public <M, V> BindingBuilder adaptItemsUsingProperty(Class<?> modelType, String propertyName) {
		IObservableMap labelProviderMap = JFaceObservableFactory.observeMap(this.itemsObservableSet, modelType, propertyName);
		this.getViewer().setLabelProvider(new ObservableMapLabelProvider(labelProviderMap));
		return this;
	}

	@Override
	public void observeProperty(Object modelObject, String propertyName) {
		this.itemsObservableSet = JFaceObservableFactory.observeSet(modelObject, propertyName);
		super.observeProperty(modelObject, propertyName);
	}
}
