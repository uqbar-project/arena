package org.uqbar.lacar.ui.impl.jface.tables;

import org.uqbar.arena.widgets.tables.ColumnLayoutBuilder;

public class DefaultColumnLayoutBuilder implements ColumnLayoutBuilder<JFaceTableLayoutBuilder> {

	@Override
	public void configure(JFaceTableLayoutBuilder tableLayoutBuilder) {
		tableLayoutBuilder.addColumnWithDefaultWeight();
	}

}
