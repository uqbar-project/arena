package org.uqbar.arena.widgets.tables;

import com.uqbar.commons.collections.Transformer;

import org.uqbar.lacar.ui.model.LabelProvider;

public class TransformerLabelProvider<From, To> implements LabelProvider<From> {

	private final Transformer<From, To> transformer;

	public TransformerLabelProvider(Transformer<From, To> transformer) {
		this.transformer = transformer;
	}

	@Override
	public void configure(LabelProviderBuilder<From> configurator) {
		configurator.addCalculatedColumn(this.transformer);
	}
}
