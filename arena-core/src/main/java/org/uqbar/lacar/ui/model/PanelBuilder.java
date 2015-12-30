package org.uqbar.lacar.ui.model;

import java.util.Map;

import org.uqbar.arena.widgets.style.Style;
import org.uqbar.arena.widgets.tree.Tree;
import org.uqbar.lacar.ui.model.builder.LinkBuilder;
import org.uqbar.lacar.ui.model.builder.StyledControlBuilder;

/**
 * @author npasserini
 */
public interface PanelBuilder {

	// ********************************************************
	// ** Components
	// ********************************************************

	public LabelBuilder addLabel();

	public StyledControlBuilder addTextBox(boolean multiLine);
	public StyledControlBuilder addPasswordField();
	public StyledControlBuilder addStyleTextArea(Map<String[], Style> configurationStyle);
	public StyledControlBuilder addNumericField(boolean withDecimals);

	public ControlBuilder addSpinner(Integer minValue, Integer maxValue);

	public ControlBuilder addCheckBox();

	public ButtonBuilder addButton(String caption, Action action);
	public LinkBuilder addLink(String caption, Action action);
	public ButtonBuilder addFileButton(String caption, String title, String path, String[] extensions);


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
	
	public PanelBuilder addChildGroup(String title);

	public void addErrorPanel(String okMessage, int preferredLines);

	// ********************************************************
	// ** Layout
	// ********************************************************

	public void setHorizontalLayout();

	public void setVerticalLayout();

	public void setLayoutInColumns(int columnCount);

	public void setPreferredWidth(int width);

}

