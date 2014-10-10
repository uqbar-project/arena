package org.uqbar.lacar.ui.model;

public interface ListBuilder<T> extends ControlBuilder {

	/**
	 * Starts the creation of a binding to the 'items' property of this Control with an observable property of the model.
	 * 
	 * @return A {@link BindingBuilder} associated to this control, which allows to further configure the
	 *         binding and has the ultimate responsibility of creating it.
	 */
	public BindingBuilder observeItems();

	public ListBuilder<T> onSelection(Action action);
}
