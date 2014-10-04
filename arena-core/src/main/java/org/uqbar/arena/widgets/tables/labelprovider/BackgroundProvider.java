package org.uqbar.arena.widgets.tables.labelprovider;

import org.uqbar.arena.widgets.tables.LabelProviderBuilder;
import org.uqbar.arena.widgets.tables.NoopTransformer;

import com.uqbar.commons.collections.Transformer;

/**
 * 
 * @author nnydejesus
 */
public class BackgroundProvider<Model, To> extends AbstractPropertyLabelProvider<Model, To> {

	public BackgroundProvider(String propertyName) {
		this(propertyName, NoopTransformer.SHARED_INSTANCE);
	}
	
	public BackgroundProvider(String propertyName, Transformer<?, To> transformer) {
		super(propertyName, transformer);
	}

	@Override
	public void configure(LabelProviderBuilder<Model> labelProviderBuilder) {
		labelProviderBuilder.observeBackgoundColumn(propertyName, transformer);
	}
	
}
