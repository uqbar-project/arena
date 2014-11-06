package org.uqbar.arena.widgets.tables.labelprovider;

import org.uqbar.arena.widgets.tables.LabelProviderBuilder;
import org.uqbar.arena.widgets.tables.NoopTransformer;

import org.apache.commons.collections15.Transformer;

/**
 * 
 * @author mpavelek
 */
public class ForegroundProvider<Model, To> extends AbstractPropertyLabelProvider<Model, To> {

	public ForegroundProvider(String propertyName) {
		this(propertyName, NoopTransformer.SHARED_INSTANCE);
	}
	
	public ForegroundProvider(String propertyName, Transformer<?, To> transformer) {
		super(propertyName, transformer);
	}

	@Override
	public void configure(LabelProviderBuilder<Model> labelProviderBuilder) {
		labelProviderBuilder.observeForegroundColumn(propertyName, transformer);
	}
	
}
