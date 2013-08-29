package org.uqbar.lacar.ui.model;


import org.uqbar.arena.widgets.tree.Tree;

/**
 * @author npasserini
 */
public interface PanelBuilder {

	// ********************************************************
	// ** Components
	// ********************************************************

	public LabelBuilder addLabel();

	public SkinnableBuilder addTextBox();

	public ControlBuilder addSpinner(Integer minValue, Integer maxValue);

	public ControlBuilder addCheckBox();

	public ButtonBuilder addButton(String caption, Action action);

	// SELECTORS
	
	public <T> ListBuilder<T> addSelector(boolean nullValue);

	public <T> ListBuilder<T> addList();
	
	public <T> ListBuilder<T> addRadioSelector();
	
	// 

	public <R> TableBuilder<R> addTable(Class<R> itemType);

	public <T> ControlBuilder addTree(Tree<T> tree, String propertyNode);

	// ********************************************************
	// ** Panels
	// ********************************************************

	public PanelBuilder addChildPanel();

	public void addErrorPanel(String okMessage);

	// ********************************************************
	// ** Layout
	// ********************************************************

	public void setHorizontalLayout();

	public void setVerticalLayout();

	public void setLayoutInColumns(int columnCount);

	public void setPreferredWidth(int width);

}
