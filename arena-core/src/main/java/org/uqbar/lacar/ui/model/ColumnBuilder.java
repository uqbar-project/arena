package org.uqbar.lacar.ui.model;

import java.awt.Color;

public interface ColumnBuilder<Row> extends WidgetBuilder {

	public void setTitle(String title);

	// ********************************************************
	// ** Column size specification
	// ********************************************************

	public void setWeight(int preferredSize);

	public void setFixedSize(int pixels);

	void setForeground(Color color);

	void setBackground(Color color);

	void setFontSize(int size);

}
