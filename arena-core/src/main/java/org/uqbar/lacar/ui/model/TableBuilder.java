package org.uqbar.lacar.ui.model;

import java.util.List;

public interface TableBuilder<Row> extends ControlBuilder {
	
	public ColumnBuilder<Row> addColumn(List<LabelProvider<Row>> labelProvider);

	public abstract BindingBuilder observeContents();
	
	public abstract void setNumberVisibleRows(int numberVisibleRows);
}
