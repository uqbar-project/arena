package org.uqbar.lacar.ui.model;

import org.uqbar.arena.widgets.tables.LabelProviderBuilder;

public interface LabelProvider<T> {
	public void configure(LabelProviderBuilder<T> configurator);
}
