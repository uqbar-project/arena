package org.uqbar.arena.bindings;

import org.uqbar.commons.model.UserException;

/**
 * Converts and validates values coming from the view to the type and/or format needed in the model, and
 * viceversa.
 * 
 * @param M El tipo de los valores que maneja el modelo.
 * @param V El tipo de los valores que maneja la vista.
 * 
 * @author npasserini
 */
public interface Transformer<M, V> {
	/**
	 * Convert and/or validate a value coming from the view to the type and/or format needed in the model.
	 * 
	 * @param valueFromView A value coming from de view
	 * @return The (validated) value to be sent to the model
	 * 
	 * @throws UserException Si hay un error que se debe mostrar al usuario.
	 * @throws Exception Si hay cualquier otro tipo de error, interno al programa.
	 */
	public M viewToModel(V valueFromView);

	/**
	 * Convert and/or validate a value coming from the model to the type and/or format needed in the view.
	 * 
	 * @param valueFromModel A value coming from the model
	 * @return The value to be sent to the view.
	 * 
	 * @throws UserException Si hay un error que se debe mostrar al usuario.
	 * @throws Exception Si hay cualquier otro tipo de error, interno al programa.
	 */
	public V modelToView(M valueFromModel);

	/**
	 * Returns the class of the values in the model side.
	 */
	public Class<M> getModelType();

	/**
	 * Returns the class of the values in the view side.
	 */
	public Class<V> getViewType();

}
