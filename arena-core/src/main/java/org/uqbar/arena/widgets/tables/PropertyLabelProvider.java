package org.uqbar.arena.widgets.tables;

import org.uqbar.lacar.ui.model.LabelProvider;

import com.uqbar.commons.collections.Transformer;

/**
 * 
 * @author npasserini
 * @param <T>
 */
public class PropertyLabelProvider<T> implements LabelProvider<T> {
	private final String propertyName;
	private Transformer<?,String> transformer;

	public PropertyLabelProvider(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public void configure(LabelProviderBuilder<T> configurator) {
		if (transformer == null)
			configurator.addPropertyMappedColumn(this.propertyName);
		else
			configurator.addPropertyMappedColumn(this.propertyName, this.transformer);
	}
	
	public <P> void setTransformer(Transformer<P, String> transformer) {
		this.transformer = transformer;
	}

}
