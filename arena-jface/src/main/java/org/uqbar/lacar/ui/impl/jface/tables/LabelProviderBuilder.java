package org.uqbar.lacar.ui.impl.jface.tables;

import com.uqbar.commons.collections.Transformer;

/**
 * 
 * @author npasserini
 * @param <R>
 */
public interface LabelProviderBuilder<R> {

	public void addPropertyMappedColumn(String propertyName);

	public void addCalculatedColumn(Transformer<R, ?> transformer);

}