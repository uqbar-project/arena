package org.uqbar.arena.layout;

import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * Elemento que se encarga de posicionar los componentes de un elemento "contenedor"
 * de UI.
 * 
 * @author npasserini
 */
public interface Layout {

	public void configure(PanelBuilder panel);

}