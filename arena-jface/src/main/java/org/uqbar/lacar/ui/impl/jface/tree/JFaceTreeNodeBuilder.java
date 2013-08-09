package org.uqbar.lacar.ui.impl.jface.tree;

import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.uqbar.arena.widgets.tree.TreeNodeBuilder;
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

public class JFaceTreeNodeBuilder<T> extends AbstractWidgetBuilder implements TreeNodeBuilder<T> {

	private final TreeViewerColumn treeViewerColumn;
	private LabelProvider<T> labelProvider;

	public JFaceTreeNodeBuilder(JFaceTreeBuilder<T> tree, LabelProvider<T> labelProvider) {
		this.labelProvider = labelProvider;
		this.treeViewerColumn = new TreeViewerColumn(tree.getJFaceTreeViewer(), SWT.NONE);
	}

	@Override
	public void pack() {
		super.pack();
		this.treeViewerColumn.getColumn().pack();
	}

	public LabelProvider<T> getLabelProvider() {
		return labelProvider;
	}

}
