package org.uqbar.lacar.ui.impl.jface.tables;

import org.apache.commons.collections15.Transformer;

/**
 * 
 * @author npasserini
 */
public interface LabelProviderBuilder<R> {

	public void addPropertyMappedColumn(String propertyName);

	public void addCalculatedColumn(Transformer<R, ?> transformer);

}