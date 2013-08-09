package org.uqbar.lacar.ui.impl.jface.tables;

import org.uqbar.arena.widgets.tables.ColumnLayoutBuilder;

public class FixedColumnLayoutBuilder implements ColumnLayoutBuilder<JFaceTableLayoutBuilder> {

	private final int pixels;

	public FixedColumnLayoutBuilder(int pixels) {
		this.pixels = pixels;
	}

	@Override
	public void configure(JFaceTableLayoutBuilder tableLayoutBuilder) {
		tableLayoutBuilder.addFixedColumn(this.pixels);
	}

}
