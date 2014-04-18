package org.uqbar.arena.widgets.tables;

import com.uqbar.commons.collections.Transformer;

import org.uqbar.lacar.ui.model.LabelProvider;

/**
 * 
 * @author nnydejesus
 */
public class BackgoundProvider<Model, From, To> implements LabelProvider<Model> {
	private final Transformer<From, To> transformer;
	private final String propertyName;

	public BackgoundProvider(String propertyName, Transformer<From, To> transformer) {
		this.propertyName = propertyName;
		this.transformer = transformer;
	}

	@Override
	public void configure(LabelProviderBuilder<Model> labelProviderBuilder) {
		labelProviderBuilder.observeBackgoundColumn(propertyName, transformer);
	}
}
