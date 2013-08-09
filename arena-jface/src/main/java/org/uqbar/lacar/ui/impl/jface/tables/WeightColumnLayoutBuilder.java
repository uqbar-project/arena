package org.uqbar.lacar.ui.impl.jface.tables;

import org.uqbar.arena.widgets.tables.ColumnLayoutBuilder;

public class WeightColumnLayoutBuilder implements ColumnLayoutBuilder<JFaceTableLayoutBuilder> {

	private final int weight;

	public WeightColumnLayoutBuilder(int weight) {
		this.weight = weight;
	}

	@Override
	public void configure(JFaceTableLayoutBuilder tableLayoutBuilder) {
		tableLayoutBuilder.addColumnWithWieght(this.weight);
	}

}
