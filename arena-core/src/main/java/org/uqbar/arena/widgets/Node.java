package org.uqbar.arena.widgets;

import org.uqbar.arena.widgets.tree.TreeNode;


public interface Node<T> {
	
	public void addTreeItem(TreeNode<T> item);
	
	public Node<T> getParent();


}
