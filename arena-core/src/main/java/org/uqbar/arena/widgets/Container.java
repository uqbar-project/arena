package org.uqbar.arena.widgets;

import org.uqbar.commons.model.IModel;

/**
 * Interfaz que implementan todos los elementos visuales que pueden contener {@link Widget}s.
 * 
 * El contenedor que implementa esta interfaz se compromete a forwardear a todos sus hijos el mensaje
 * {@link Widget#showOn(org.uqbar.lacar.ui.model.PanelBuilder)} a cada uno de sus hijos, de esta manera
 * permita que sean incluidos en la traducción final.
 * 
 * @author npasserini
 */
public interface Container {
	
	/**
	 * The business model associated to this container. The child {@link Widget}s of this container will be
	 * display properties of this model (with few exceptions).
	 * 
	 * @return The model.
	 */
	public IModel<?> getModel();
	
	/**
	 * Agrega un {@link Widget} de este contenedor.
	 * 
	 * ATENCION: Los {@link Widget} se agregan a su {@link Container} por sí mismos en el momento de su
	 * creación. No se debe invocar este método manualmente.
	 * 
	 * @param child El {@link Widget} agregar.
	 */
	public void addChild(Widget child);

}
