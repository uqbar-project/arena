package org.uqbar.lacar.ui.impl.jface.tree;

import java.util.ArrayList;
import java.util.List;

abstract class TreeNode<T> implements ITreeNode<T> {
	protected ITreeNode<T> fParent;
	protected List<T> fChildren;

	public TreeNode(ITreeNode<T> parent) {
		fParent = parent;
	}

	public boolean hasChildren() {
		return true;
	}

	public ITreeNode<T> getParent() {
		return fParent;
	}

	public List<T> getChildren() {
		if (fChildren != null)
			return fChildren;

		fChildren = new ArrayList<>();
		createChildren(fChildren);

		return fChildren;
	}

	/* subclasses should override this method and add the child nodes */
	protected abstract void createChildren(List<T> children);
}
