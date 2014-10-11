package org.uqbar.lacar.ui.model.bindings;

import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;

/**
 * Representa una propiedad una vista que puede ser utilizada dentro de un binding.
 * En realidad es el objeto que sabe crear un bindingbuilder.
 * 
 * La creación de un binding entre un control (vista) y un modelo arranca
 * por la vista.
 * El control instancia uno de estos objetos dependiendo de qué evento
 * se quiere vincular de la vista (selección, o la lista de opciones,
 * o el color de fondo, etc).
 * Allí aparece el {@link BindingBuilder} que ya sabe qué parte
 * de la vista vincula, pero todavía no qué parte del modelo.
 * 
 * @see BindingBuilder
 * 
 * @author npasserini
 */
public interface ViewObservable<V extends Widget, C extends WidgetBuilder> {

	/**
	 * The most important method from a user point-of-view.
	 * Once you have one of this object you can bind it to another Observable object.
	 */
	public <M> Binding<M, V, C> bindTo(Observable<M> observable);
	
	/** 
	 * Shortcut method.
	 * Binds this observable against an observable model's property.
	 */
	public <M> Binding<M, V, C> bindToProperty(String propertyName);
	
	/**
	 * Executes the actual binding between widget and model.
	 */
	public BindingBuilder createBinding(C control);
	
	/**
	 * Returns the owner of the view observable.
	 * Ex:
	 * 	textBox.value().getView() == textBox
	 */
	public V getView();

}
