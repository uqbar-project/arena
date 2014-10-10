package org.uqbar.arena.widgets.tree;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.widgets.Container;
import org.uqbar.arena.widgets.Control;
import org.uqbar.arena.widgets.Node;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;

public class Tree<T>  extends Control implements Node<T>{
	private static final long serialVersionUID = 1L;
	private Action onClickItem;
	private Action onExpand;

	/**
	 * Los componentes contenidos en este {@link Panel}
	 */
	private List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();
	private String propertyNode;
	
	
	public Tree(Container container) {
		super(container);
	}
	
	public void addTreeItem(TreeNode<T> treeItem){
		this.children.add(treeItem);
	}
	
	
	/**
	 * Asigna el contenido de esta Arbol.
	 * 
	 * @param model Una característica observable del modelo.
	 * 
	 * @return Este mismo Árbol, para enviar mensajes anidados
	 */
	public Binding<?,Tree, TreeBuilder<?>> bindContents(Observable model) {
		return this.addBinding(model, new ObservableTreeContents(this));
	}
	
	public Binding<?,Tree,TreeBuilder<?>> bindContentsToProperty(String parentPropertyName, String childrenPropertyName) {
		return this.bindContents(new ObservableTwoProperty(parentPropertyName, childrenPropertyName));
	}
	
	public Tree<T> bindNodeToProperty(String propertyName){
		this.propertyNode = propertyName;
		return this;
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		return container.addTree(this, propertyNode);
	}
	
	
	public Tree<T> onClickItem(Action onClickItem) {
		this.onClickItem = onClickItem;
		return this;
	}
	
	public Tree<T> onExpand(Action onExpand) {
		this.onExpand = onExpand;
		return this;
	}


	public List<TreeNode<T>> getChildren() {
		return children;
	}

	@Override
	public Node<T> getParent() {
		return null;
	}
	
	public Action getOnClickItem() {
		return onClickItem;
	}
	
	public Action getOnExpand() {
		return onExpand;
	}

}
