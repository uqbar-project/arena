package org.uqbar.lacar.ui.impl.jface.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.uqbar.arena.widgets.tree.TreeBuilder;
import org.uqbar.arena.widgets.tree.TreeNodeBuilder;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.impl.jface.lists.SelectionChangeListener;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

public class JFaceTreeBuilder<R> extends JFaceControlBuilder<Tree> implements TreeBuilder<R>{

	private TreeViewer viewer;
	private final org.uqbar.arena.widgets.tree.Tree<R> arenaTree;
	private List<JFaceTreeNodeBuilder<R>> childs;
	private String propertyNode;


	public JFaceTreeBuilder(JFaceContainer container, org.uqbar.arena.widgets.tree.Tree<R> arenaTree, String propertyNode) {
		super(container);
		this.arenaTree = arenaTree;
		this.propertyNode= propertyNode;
		this.childs = new ArrayList<JFaceTreeNodeBuilder<R>>();
		this.viewer = this.createTree(this.getContainer().getJFaceComposite());
		this.initialize(this.viewer.getTree());	
		
	}


	private TreeViewer createTree(Composite jFaceComposite) {
		final TreeViewer treeViewer = new TreeViewer(jFaceComposite, SWT.MULTI | SWT.H_SCROLL	| SWT.V_SCROLL);
		treeViewer.setLabelProvider(new ReflectionLabelProvider(propertyNode));
		if(arenaTree.getOnClickItem() != null){
			treeViewer.addSelectionChangedListener(new SelectionChangeListener(arenaTree.getOnClickItem()));
		}
		
		return treeViewer;
	}
	
	// ********************************************************
	// ** Description 
	// ********************************************************

	@Override
	public TreeNodeBuilder<R> addNode(LabelProvider<R> labelProvider) {
		JFaceTreeNodeBuilder<R> child = new JFaceTreeNodeBuilder<R>(this, labelProvider);
		childs.add(child);
		return child;
	};

	@Override
	public BindingBuilder observeContents() {
		return new JFaceTreeItemsBindingBuilder(this);
	}
	

	@Override
	protected Control getControlLayout() {
		return this.viewer.getControl();
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, ViewersObservables.observeSingleSelection(this.getJFaceTreeViewer()));
	}
	
	public TreeViewer getJFaceTreeViewer() {
		return viewer;
	}


	public List<JFaceTreeNodeBuilder<R>> getChilds() {
		return childs;
	}


	public org.uqbar.arena.widgets.tree.Tree<R> getArenaTree() {
		return arenaTree;
	}



}
