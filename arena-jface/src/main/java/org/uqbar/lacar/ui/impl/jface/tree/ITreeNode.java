package org.uqbar.lacar.ui.impl.jface.tree;

import java.util.List;

import org.eclipse.swt.graphics.Image;

interface ITreeNode
{
	public String getName();
	public Image getImage();
	public List getChildren();
	public boolean hasChildren();
	public ITreeNode getParent();
}
