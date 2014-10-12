package org.uqbar.arena.widgets.tables.labelprovider;

import org.uqbar.arena.widgets.tables.LabelProviderBuilder;

/**
 * 
 * @author npasserini
 */
public class PropertyLabelProvider<T> extends AbstractPropertyLabelProvider<T, String> {

	public PropertyLabelProvider(String propertyName) {
		super(propertyName);
	}

	@Override
	public void configure(LabelProviderBuilder<T> configurator) {
		if (transformer == null)
			configurator.addPropertyMappedColumn(this.propertyName);
		else
			configurator.addPropertyMappedColumn(this.propertyName, this.transformer);
	}

}
