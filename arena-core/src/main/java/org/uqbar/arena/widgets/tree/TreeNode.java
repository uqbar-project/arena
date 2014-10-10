package org.uqbar.arena.widgets.tree;

import org.uqbar.arena.widgets.Node;
import org.uqbar.arena.widgets.tables.labelprovider.PropertyLabelProvider;
import org.uqbar.arena.widgets.tables.labelprovider.TransformerLabelProvider;
import org.uqbar.lacar.ui.model.LabelProvider;

import com.uqbar.commons.collections.Transformer;

public class TreeNode<T> implements Node<T> {

	private Node<T> parent;
	private LabelProvider<T> labelProvider;
	private Object data;

	public TreeNode(Tree<T> parent) {
		this.parent = parent;
		parent.addTreeItem(this);
	}

	public TreeNode(TreeNode<T> parent) {
		this.parent = parent;
		parent.getParent().addTreeItem(this);
	}

	public void addTreeItem(TreeNode<T> treeItem) {
		this.getParent().addTreeItem(treeItem);
	}

	@Override
	public Node<T> getParent() {
		return parent;
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	public TreeNode<T> bindContentsToProperty(String propertyName) {
		this.labelProvider = new PropertyLabelProvider<T>(propertyName);
		return this;
	}

	public <U> TreeNode<T> bindContentsToTransformer(Transformer<T, U> transformer) {
		this.labelProvider = new TransformerLabelProvider<T, U>(transformer);
		return this;
	}
	
	// ********************************************************
	// ** Rendering
	// ********************************************************

	public void showOn(TreeBuilder<T> tree) {
		tree.addNode(this.labelProvider);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
