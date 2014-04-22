package org.uqbar.lacar.ui.model;

import org.uqbar.arena.bindings.Transformer;

/**
 * Colabora en la construcción de un binding.
 * 
 * @param<A> Adapter type
 * @author npasserini
 */
public interface BindingBuilder {

	// ********************************************************
	// ** Configuracion
	// ********************************************************

	/**
	 * Observes a property of a known object.
	 * 
	 * @param model The owner of the property to be observed.
	 * @param propertyName The name of the property to observe.
	 */
	public void observeProperty(Object model, String propertyName);

	/**
	 * Sets an adapter strategy based on a {@link Transformer}. As the transformer is bidirectional, it can
	 * transform the values from the model to the value needed in the view and viceversa.
	 * 
	 * THIS IS A SHORTCUT METHOD WHEN YOU NEED TO TRANSFORM IN BOTH WAYS.
	 * OTHERWISE USE {@link #modelToView(com.uqbar.commons.collections.Transformer)} and
	 * {@link #viewToModel(com.uqbar.commons.collections.Transformer)}
	 * 
	 * @param transformer The strategy
	 * @return this.
	 */
	public <M, V> BindingBuilder adaptWith(Transformer<M, V> transformer);
	
	/**
	 * Configures the given "one-way" transformer to transform values coming from the VIEW
	 * that will be set into the model.
	 * @param transformer
	 * @return
	 */
	public <M, V> BindingBuilder viewToModel(com.uqbar.commons.collections.Transformer<V, M> transformer);
	
	/**
	 * Configures the given "one-way" transformer to transform values coming from the MODEL
	 * that will be set into the VIEW.
	 * @param transformer
	 * @return
	 */
	public <M, V> BindingBuilder modelToView(com.uqbar.commons.collections.Transformer<M, V> transformer);

	// ********************************************************
	// ** Build
	// ********************************************************

	/**
	 * Indicates that configuration of this builder has ended and gives place to the work it needs to do to
	 * finalize de construction.
	 */
	public void build();

}
