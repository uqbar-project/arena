package org.uqbar.arena.widgets.tables;

import com.uqbar.commons.collections.Transformer;

/**
 * 
 * @author npasserini
 * @param <R>
 */
public interface LabelProviderBuilder<R> {

	public void addPropertyMappedColumn(String propertyName);
	public <P> void addPropertyMappedColumn(String propertyName, final Transformer<P, String> transformer);
	
	/**
	 * @deprecated should use {@link #addPropertyMappedColumn(String, Transformer)}
	 * @param transformer
	 */
	public void addCalculatedColumn(Transformer<R, ?> transformer);
	
	public void observeBackgoundColumn(String propertyName, Transformer<?, ?> transformer);
	
	public void observeForegroundColumn(String propertyName, Transformer<?, ?> transformer);

}