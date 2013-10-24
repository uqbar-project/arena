package org.uqbar.lacar.ui.impl.jface;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.scalatest.Entry;
import org.uqbar.arena.widgets.style.Style;
import org.uqbar.arena.widgets.tree.Tree;
import org.uqbar.lacar.ui.impl.jface.bindings.ObservableErrorPanelForegroundColor;
import org.uqbar.lacar.ui.impl.jface.bindings.ObservableStatusMessage;
import org.uqbar.lacar.ui.impl.jface.lists.JFaceListBuilder;
import org.uqbar.lacar.ui.impl.jface.radiogroup.JFaceRadioGroupBuilder;
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableBuilder;
import org.uqbar.lacar.ui.impl.jface.tree.JFaceTreeBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.LabelBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.SkinnableBuilder;
import org.uqbar.lacar.ui.model.TableBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.ui.view.ErrorViewer;

import com.uqbar.commons.collections.CollectionFactory;

/**
 * 
 * @author npasserini
 */
public class JFacePanelBuilder extends JFaceWidgetBuilder<Composite> implements PanelBuilder, JFaceContainer {
	private List<WidgetBuilder> children = CollectionFactory.createList();

	public JFacePanelBuilder(JFaceContainer container) {
		this(container, new Composite(container.getJFaceComposite(), SWT.NONE));
	}
	
	public JFacePanelBuilder(JFaceContainer container, Composite composite) {
		super(container, composite);
	}

	// ********************************************************
	// ** Components
	// ********************************************************

	@Override
	public LabelBuilder addLabel() {
		return new JFaceLabelBuilder(this);
	}

	@Override
	public SkinnableBuilder addTextBox() {
		return new JFaceTextBuilder(this);
	}
	

	@Override
	public SkinnableBuilder addStyleTextArea(List<Entry<String[], Style>> configurationStyle) {
		return new  JFaceStyledTextBuilder(this, configurationStyle);
	}
	
	@Override
	public ControlBuilder addSpinner(Integer minValue, Integer maxValue) {
		return new JFaceSpinnerBuilder(this, minValue, maxValue);
	}

	@Override
	public <T> ListBuilder<T> addSelector(boolean nullValue) {
		// Combo jfaceCombo = new Combo(this.getWidget(), SWT.LEFT | SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		//return new JFaceSelectorBuilder(jfaceCombo, this, options, descriptionProperty,	nullValue).onSelection(onSelection);
		
		return new JFaceSelectorBuilder<T>(this);
	}

	@Override
	public ControlBuilder addCheckBox() {
		Button jfaceWidget = new Button(this.getWidget(), SWT.CHECK);

		// TODO Esto estaba hardcodeado y ahora lo saqué, ¿se puede volarlo del todo?
		// check.setLayoutData(new GridData(150, SWT.DEFAULT));

		JFaceCheckBoxBuilder widget = new JFaceCheckBoxBuilder(jfaceWidget, this);

		return widget;
	}

	@Override
	public ButtonBuilder addButton(String caption, Action action) {
		return new JFaceButtonBuilder(this).setCaption(caption).onClick(action);
	}
	
	@Override
	public ButtonBuilder addFileButton(String caption, String title, String path, String[] extensions) {
		return new JFaceFileSelectorBuilder(this, caption, title, path, extensions);
	}

	@Override
	public <R> TableBuilder<R> addTable(Class<R> itemType) {
		return new JFaceTableBuilder<R>(this, itemType);
	}
	
	@Override
	public <T> ControlBuilder addTree(Tree<T> tree, String propertyNode) {
		return new JFaceTreeBuilder<T>(this, tree, propertyNode);
	}
	
	@Override
	public <T> ListBuilder<T> addList() {
		return new JFaceListBuilder<T>(this);
	}
	
	@Override
	public <T> ListBuilder<T> addRadioSelector() {
		return new JFaceRadioGroupBuilder<T>(this);
	}

	// ********************************************************
	// ** Panel
	// ********************************************************

	@Override
	public PanelBuilder addChildPanel() {
		return new JFacePanelBuilder(this);
	}
	
	@Override
	public PanelBuilder addChildGroup(String title) {
		Group group = new Group(this.getJFaceComposite(), SWT.SHADOW_IN);
		group.setText(title);
		return new JFacePanelBuilder(this, group);
	}

	@Override
	public void addErrorPanel(String okMessage) {
		// TODO Usar el framework para configurar el label en lugar de hacerlo manualmente.
		Label errorLabel = new Label(this.getWidget(), SWT.WRAP);

		errorLabel.setLayoutData(new RowData(250, 50));

		// fija el background del label. por default es blanco, al igual que el de eclipse
		errorLabel.setBackground(this.getWidget().getDisplay().getSystemColor(SWT.COLOR_WHITE));

		JFaceControlBuilder<Label> labelBuilder = new JFaceLabelBuilder(this, errorLabel);
		labelBuilder.bind(//
			new ObservableStatusMessage(this.getStatus(), okMessage), //
			SWTObservables.observeText(errorLabel));

		labelBuilder.bind(//
			SWTObservables.observeForeground(errorLabel),//
			new ObservableErrorPanelForegroundColor(this.getStatus()));

	}

	@Override
	public void setHorizontalLayout() {
		this.setLayout(new RowLayout(SWT.HORIZONTAL));
	}

	protected void setLayout(Layout layout) {
		this.getWidget().setLayout(layout);
	}

	@Override
	public void setVerticalLayout() {
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.fill = true;
		this.setLayout(layout);
	}

	@Override
	public void setLayoutInColumns(int columnCount) {
		this.setLayout(new GridLayout(columnCount, false));
	}

	@Override
	public void setPreferredWidth(int width) {
		// TODO Hacer una abstracción más genérica que permita manejar distintos tipos de Layout Data.
		// this.getWidget().setLayoutData(new RowData(width , 500));
	}

	// ********************************************************
	// ** JFaceContainer
	// ********************************************************

	@Override
	public ErrorViewer getErrorViewer() {
		return this.getContainer().getErrorViewer();
	}

	@Override
	public AggregateValidationStatus getStatus() {
		return this.getContainer().getStatus();
	}

	@Override
	public Composite getJFaceComposite() {
		return this.getWidget();
	}

	@Override
	public JFaceContainer addChild(WidgetBuilder child) {
		this.children.add(child);
		return this;
	}

	@Override
	public void pack() {
		for (WidgetBuilder child : this.children) {
			child.pack();
		}
	}
}
