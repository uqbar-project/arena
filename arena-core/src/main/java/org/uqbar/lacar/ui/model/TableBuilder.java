package org.uqbar.lacar.ui.model;

public interface TableBuilder<Row> extends ControlBuilder {
	
	public ColumnBuilder<Row> addColumn(LabelProvider<Row> labelProvider);

	public abstract BindingBuilder observeContents();
}
