package org.uqbar.lacar.ui.impl.jface.tree;

import java.util.List;

import org.eclipse.swt.graphics.Image;

interface ITreeNode<T> {
	public String getName();

	public Image getImage();

	public List<T> getChildren();

	public boolean hasChildren();

	public ITreeNode<T> getParent();
}
