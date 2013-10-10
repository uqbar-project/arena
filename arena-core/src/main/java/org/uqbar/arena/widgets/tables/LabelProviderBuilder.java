package org.uqbar.arena.widgets.tables;

import com.uqbar.commons.collections.Transformer;

/**
 * 
 * @author npasserini
 * @param <R>
 */
public interface LabelProviderBuilder<R> {

	public void addPropertyMappedColumn(String propertyName);

	public void addCalculatedColumn(Transformer<R, ?> transformer);
	public void observeBackgoundColumn(String propertyName, Transformer<?, ?> transformer);

}