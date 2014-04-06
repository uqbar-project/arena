package org.uqbar.lacar.ui.model;

public interface TreeItemsBindingBuilder extends ItemsBindingBuilder {

	public void observeProperty(Object model, String parentPropertyName, String childPropertyName);

}
