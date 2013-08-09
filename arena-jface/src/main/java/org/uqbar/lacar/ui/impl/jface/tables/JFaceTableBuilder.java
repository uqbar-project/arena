package org.uqbar.lacar.ui.impl.jface.tables;

import java.util.List;

import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ColumnBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;
import org.uqbar.lacar.ui.model.TableBuilder;

import com.uqbar.commons.collections.CollectionFactory;

/**
 * Implementation of {@link TableBuilder} based on a JFace {@link TableViewer}.
 * 
 * @author npasserini
 * 
 * @param <R> The type of objects (models) shown in each row of the table.
 */
public class JFaceTableBuilder<R> extends JFaceControlBuilder<Table> implements TableBuilder<R> {
	private TableViewer viewer;
	private List<JFaceColumnBuilder<R>> columns = CollectionFactory.createList();
	private Class<R> itemType;

	// ********************************************************
	// ** Creation
	// ********************************************************

	public JFaceTableBuilder(JFaceContainer container, Class<R> itemType) {
		super(container);

		this.itemType = itemType;
		this.viewer = this.createTableViewer(this.getContainer().getJFaceComposite());
		this.initialize(this.getViewer().getTable());
	}

	protected org.eclipse.swt.graphics.Color getSWTColor(java.awt.Color color) {
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(getWidget().getDisplay(), red,
			green, blue);
		return swtColor;
	}

	private TableViewer createTableViewer(Composite jFaceComposite) {
		TableViewer viewer = new TableViewer(jFaceComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLayout(new TableLayout());
		return viewer;
	}

	// ********************************************************
	// ** Description (columns)
	// ********************************************************

	@Override
	public ColumnBuilder<R> addColumn(LabelProvider<R> labelProvider) {
		JFaceColumnBuilder<R> column = new JFaceColumnBuilder<R>(this, labelProvider);
		this.columns.add(column);
		return column;
	};

	// ********************************************************
	// ** Binding
	// ********************************************************

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, ViewersObservables.observeSingleSelection(this.getViewer()));
		// return new JFaceBindingBuilder(this, new TableObservableValue(this.getWidget(), this.itemType));
	};

	@Override
	public BindingBuilder observeContents() {
		return new JFaceTableItemsBindingBuilder<R>(this);
	}

	// ********************************************************
	// ** Compilation
	// ********************************************************

	@Override
	public void pack() {
		this.getViewer().getTable().setLayout(new JFaceTableLayoutBuilder(this).createLayout());

		for (JFaceColumnBuilder<R> column : this.columns) {
			column.pack();
		}
		super.pack();
	}

	// ********************************************************
	// ** Internal accessors
	// ********************************************************

	@Override
	protected Control getControlLayout() {
		return this.getViewer().getControl();
	}

	public TableViewer getJFaceTableViewer() {
		return this.getViewer();
	}

	public List<JFaceColumnBuilder<R>> getColumns() {
		return this.columns;
	}

	public Class<R> getItemType() {
		return this.itemType;
	}

	public TableViewer getViewer() {
		return viewer;
	}
}
