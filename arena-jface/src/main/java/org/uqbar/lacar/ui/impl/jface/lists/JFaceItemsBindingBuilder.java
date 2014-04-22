package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.uqbar.arena.bindings.Transformer;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory;
import org.uqbar.lacar.ui.model.BindingBuilder;

/**
 * Base class for creating bindings for the items of a structured viewer.
 * 
 * @author npasserini
 */
public class JFaceItemsBindingBuilder implements BindingBuilder {
	private StructuredViewer viewer;
	private IObservableList itemsObservableList;

	public JFaceItemsBindingBuilder(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	/**
	 * This operation is currently not supported for this kind of binding.
	 */
	@Override
	public <M, V> BindingBuilder adaptWith(Transformer<M, V> transformer) {
		throw new UnsupportedOperationException("Applying transformers to item bindings is currently not supported.");
	}
	
	@Override
	public <M, V> BindingBuilder modelToView(com.uqbar.commons.collections.Transformer<M, V> transformer) {
		throw new UnsupportedOperationException("Applying transformers to item bindings is currently not supported.");
	}
	
	@Override
	public <M, V> BindingBuilder viewToModel(com.uqbar.commons.collections.Transformer<V, M> transformer) {
		throw new UnsupportedOperationException("Applying transformers to item bindings is currently not supported.");
	}

	@Override
	public void observeProperty(Object modelObject, String propertyName) {
		this.itemsObservableList = JFaceObservableFactory.observeList(modelObject, propertyName);
		this.viewer.setContentProvider(new ObservableListContentProvider());
	}

	// ********************************************************
	// ** Building
	// ********************************************************

	@Override
	public void build() {
		this.viewer.setInput(this.itemsObservableList);
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************

	protected StructuredViewer getViewer() {
		return viewer;
	}
}