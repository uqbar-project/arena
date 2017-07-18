package org.uqbar.lacar.ui.model;

import org.uqbar.arena.windows.MessageBox;
import org.uqbar.ui.view.ErrorViewer;

/**
 * Interfaz independiente de la tecnología que permite manipular las ventanas físicas sin acoplarse a ellas.
 * 
 * Permite tanto construir ventanas como manipular posterior mente las ventanas construidas.
 * 
 * Además por ser subinterfaz de {@link WindowFactory} permite crear ventanas hijas.
 * 
 * @author npasserini
 */
public interface WindowBuilder extends WindowFactory, WidgetBuilder {

	// ********************************************************
	// ** Configuración de la ventana
	// ********************************************************

	public void setTitle(String name);

	public void setContents(ViewDescriptor<PanelBuilder> windowDescriptor);

	public void setIcon(String iconImage);

	/**
	 * Registra el objeto que recibirá los eventos de error que ocurran dentro de la ventana.
	 */
	// TODO Tal vez esto podría ser #addErrorListener y llevarlo a una metáfora de eventos.
	public void setErrorViewer(ErrorViewer errorViewer);
	

	// ********************************************************
	// ** Acciones de la ventana
	// ********************************************************

	public void open();

	public void showMessage(MessageBox.Type type, String message);

	public void close();

	public void setMinHeight(int minHeight);

	public void setMinWidth(int minWidth);
	
}
