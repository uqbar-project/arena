package org.uqbar.lacar.ui.impl.jface.builder.tree

import org.uqbar.lacar.ui.model.AbstractWidgetBuilder
import org.uqbar.arena.widgets.tree.TreeNodeBuilder
import org.eclipse.jface.viewers.TreeViewerColumn
import org.eclipse.swt.SWT
import org.uqbar.lacar.ui.model.LabelProvider

class JFaceTreeNodeBuilder[T](tree:JFaceTreeBuilder[T], var labelProvider:LabelProvider[T])
	extends AbstractWidgetBuilder with TreeNodeBuilder[T] {
	
  private var treeViewerColumn = new TreeViewerColumn(tree.getJFaceTreeViewer(), SWT.NONE)

	override def pack() = {
		super.pack
		treeViewerColumn.getColumn.pack
	}

}
