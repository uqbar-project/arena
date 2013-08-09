package org.uqbar.lacar.ui.impl.jface.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.uqbar.arena.bindings.Transformer;
import org.uqbar.lacar.ui.model.BindingBuilder;


public class JFaceTreeContentsBindingBuilder implements BindingBuilder {

	private TreeViewer treeViewer;

	public JFaceTreeContentsBindingBuilder(JFaceTreeBuilder<?> list) {
		this.treeViewer = list.getJFaceTreeViewer();
	}

	public void observeProperty(Object model, String parentPropertyName, String childPropertyName) {
		// ATENCION, el content provider DEBE ser asignado ANTES que el input.
		this.treeViewer.setContentProvider(new TreeContentProvider(parentPropertyName, childPropertyName));
		this.treeViewer.setInput(model);
	}

	@Override
	public <M, V> BindingBuilder adaptWith(Transformer<M, V> transformer) {
		throw new UnsupportedOperationException("No está preparado para tener adapters.");
	}

	@Override
	public void observeProperty(Object model, String propertyName) {
		throw new UnsupportedOperationException("No se puede usar este binding, utilize " +
				" observeProperty(ObservableObject model, String parentPropertyName, String childPropertyName)");
	}
	
	@Override
	public void build() {
	}

}
