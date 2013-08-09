package org.uqbar.arena.widgets;

import org.uqbar.arena.bindings.ObservableItems;
import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.ValueHolder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

/**
 * @author npasserini
 */
public class Selector<T> extends Control {
	private boolean nullValue;
	protected Action onSelection;

	public Selector(Container container) {
		super(container);
	}

	public Selector<T> setContents(final java.util.List<T> options, String descriptionProperty) {
		Object valueObject = new ValueHolder<java.util.List<T>>(options);

		this.bindItems(new ObservableProperty(valueObject, ValueHolder.VALUE)) //
			//.setAdapter(new PropertyAdapter(null, descriptionProperty))
		;

		return this;
	}

	// ********************************************************
	// ** Bindings
	// ********************************************************

	/**
	 * Binds the list of items in this Combo with a property in the container model.
	 * 
	 * @param modelProperty The name of the property to bind.
	 * @return A {@link Binding} object that allows to further configure this binding.
	 */
	public Binding<ListBuilder<T>> bindItemsToProperty(String modelProperty) {
		return this.bindItems(new ObservableProperty(modelProperty));
	}

	/**
	 * Binds the list of items in this Combo to an {@link ObservableProperty}
	 * 
	 * @param modelObservable An {@link ObservableProperty} to bind.
	 * @return A {@link Binding} object that allows to further configure this binding.
	 */
	public Binding<ListBuilder<T>> bindItems(ObservableProperty modelObservable) {
		modelObservable.setContainer(this.getContainer());

		return this.addBinding(new Binding<ListBuilder<T>>(modelObservable, new ObservableItems<T, ListBuilder<T>>()));
	}

	// ********************************************************
	// ** Other configurations
	// ********************************************************

	public Selector<T> allowNull(boolean nullValue) {
		this.nullValue = nullValue;
		return this;
	}

	public Selector<T> onSelection(Action onSelection) {
		this.onSelection = onSelection;
		return this;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		// TODO Agregar esta validación de otra manera.
		// ProgramException.assertNotNull(this.options,
		// "Tried to create a selector without setting the options!");
		ListBuilder<Object> listBuilder = container.addSelector(this.nullValue);
		if (this.onSelection != null) {
			listBuilder.onSelection(this.onSelection);
		}

		return listBuilder;
	}
}
