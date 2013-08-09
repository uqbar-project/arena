package org.uqbar.arena.layout;

import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * 
 * @author npasserini
 */
public class ColumnLayout implements Layout {
	private final int columnCount;

	public ColumnLayout(int columnCount) {
		this.columnCount = columnCount;
	}

	@Override
	public void configure(PanelBuilder panel) {
		panel.setLayoutInColumns(this.columnCount);
	}

}
