package org.uqbar.lacar.ui.impl.jface.tree;

import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.uqbar.lacar.ui.model.Action;

public class TreeListener implements ITreeViewerListener{
	
	private Action onTreeCollapsed;
	private Action onTreeExpanded;
	
	
	public TreeListener(Action onTreeCollapsed, Action onTreeExpanded ) {
		this.onTreeCollapsed = onTreeCollapsed;
		this.onTreeExpanded = onTreeExpanded;
	}
	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		if (onTreeCollapsed != null) {
//			onTreeCollapsed.execute(event.getElement());
		}
	}
	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		if (onTreeExpanded != null) {
//			onTreeExpanded.execute(event.getElement());
		}
	}

}
