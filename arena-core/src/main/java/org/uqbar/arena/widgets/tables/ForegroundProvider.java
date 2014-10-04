package org.uqbar.arena.widgets.tables;

import org.uqbar.lacar.ui.model.LabelProvider;

import com.uqbar.commons.collections.Transformer;

/**
 * 
 * @author mpavelek
 */
public class ForegroundProvider<Model, From, To> implements LabelProvider<Model> {
	private Transformer<From, To> transformer;
	private final String propertyName;

	public ForegroundProvider(String propertyName) {
		this(propertyName, NoopTransformer.SHARED_INSTANCE);
	}
	
	public ForegroundProvider(String propertyName, Transformer<From, To> transformer) {
		this.propertyName = propertyName;
		this.transformer = transformer;
	}

	@Override
	public void configure(LabelProviderBuilder<Model> labelProviderBuilder) {
		labelProviderBuilder.observeForegroundColumn(propertyName, transformer);
	}
	
	public void setTransformer(Transformer<From, To> transformer) {
		this.transformer = transformer;
	}
}
