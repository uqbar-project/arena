package org.uqbar.lacar.ui.impl.jface.builder.tree

import org.uqbar.lacar.ui.impl.jface.builder.JFaceControlBuilder
import org.eclipse.swt.widgets.Tree
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.arena.widgets.tree.TreeBuilder
import java.util.ArrayList
import org.eclipse.swt.widgets.Composite
import org.eclipse.jface.viewers.TreeViewer
import org.uqbar.eclipse.jface.ReflectionLabelProvider
import org.eclipse.swt.SWT
import org.uqbar.arena.jface.JFaceImplicits._
import org.uqbar.lacar.ui.model.LabelProvider
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.eclipse.jface.databinding.viewers.ViewersObservables

class JFaceTreeBuilder[R](container:JFaceContainer, _arenaTree:org.uqbar.arena.widgets.tree.Tree[R], var propertyNode:String)
extends JFaceControlBuilder[Tree](container) with TreeBuilder[R] {
	
	private var arenaTree = _arenaTree
	private var childs = new ArrayList[JFaceTreeNodeBuilder[R]]() 
	private var viewer = this.createTree(this.getContainer().getJFaceComposite()) 
	this.initialize(this.viewer.getTree());	

	def createTree(jFaceComposite:Composite) = {
		val treeViewer = new TreeViewer(jFaceComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setLabelProvider(new ReflectionLabelProvider(propertyNode));
		if (arenaTree.getOnClickItem() != null) {
			treeViewer.addSelectionChangedListener(arenaTree.getOnClickItem())
		}
		treeViewer
	}
	
	override def addNode(labelProvider:LabelProvider[R]) = {
		val child = new JFaceTreeNodeBuilder[R](this, labelProvider)
		childs.add(child)
		child
	}

	override def observeContents() = new JFaceTreeItemsBindingBuilder(this)
	override def observeValue() = new JFaceBindingBuilder(this, ViewersObservables.observeSingleSelection(getJFaceTreeViewer))

	override def getControlLayout() = viewer getTree
	def getJFaceTreeViewer() = viewer
	def getChilds() = childs
	def getArenaTree() = arenaTree
	
}