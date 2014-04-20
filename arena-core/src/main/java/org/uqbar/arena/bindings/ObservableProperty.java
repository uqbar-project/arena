package org.uqbar.arena.bindings;

import org.uqbar.arena.widgets.Container;
import org.uqbar.commons.model.IModel;
import org.uqbar.commons.model.Model;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.bindings.Observable;

/**
 * Observa una propiedad de un modelo.
 * 
 * @author npasserini
 */
public class ObservableProperty implements Observable {
	private IModel<?> model;
	protected final String propertyName;

	public ObservableProperty(String propertyName) {
		this.propertyName = propertyName;
	}

	public ObservableProperty(IModel<?> model, String propertyName) {
		this(propertyName);
		this.model = model;
	}

	public ObservableProperty(Object modelObject, String propertyName) {
		this(new Model<Object>(modelObject), propertyName);
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	/**
	 * Si a este {@link ObservableProperty} no se le ha asignado un modelo, tomará como base el modelo del
	 * contenedor.
	 */
	@Override
	public void setContainer(Container container) {
		if (this.model == null) {
			this.setModel(container.getModel());
		}
	}

	/**
	 * Antes de registrar el modelo se lo valida, en caso de fallar la validación el modelo no se asigna.
	 * Widget
	 * 
	 * @param model El modelo contra el que bindear
	 * @return Este mismo {@link ObservableProperty}, comodidad para enviar mensajes anidados.
	 */
	public ObservableProperty setModel(IModel<?> model) {
		// Validar que el contenedor tiene la propiedad que nos interesa.
		// model.getGetter(this.propertyName);
		this.model = model;
		return this;
	}

	// ********************************************************
	// ** Building
	// ********************************************************

	@Override
	public void configure(BindingBuilder binder) {
		binder.observeProperty(this.model.getSource(), this.propertyName);
	}
}
