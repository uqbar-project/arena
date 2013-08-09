package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.ui.view.ErrorViewer;

/**
 * Es simplemente una agrupación de diferentes elementos que están presentes en toda ventana y son usados por
 * muchos componentes, empaquetarlos aquí simplemente permite pasar un único parámetro.
 * 
 * De paso está encapsulado y sólo los objetos que realmente usan los elementos aquí provistos son afectados
 * si se agrega un elemento nuevo.
 * 
 * @author npasserini
 * 
 */
public interface JFaceContainer {

	public DataBindingContext getDataBindingContext();

	public ErrorViewer getErrorViewer();

	public AggregateValidationStatus getStatus();

	public Composite getJFaceComposite();

	/**
	 * Agrega un hijo al contenedor. Esto es fundamental para que el contenedor al finalizar su construcción
	 * pueda enviar el mensaje {@link WidgetBuilder#pack()} a toda su descendencia.
	 * 
	 * @return Este mismo container, utilidad para anidar mensajes.
	 */
	public JFaceContainer addChild(WidgetBuilder child);
}
