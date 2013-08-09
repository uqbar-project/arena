package org.uqbar.arena.widgets.tables;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.widgets.Container;
import org.uqbar.arena.widgets.Control;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.TableBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;

/**
 * 
 * 
 * @author npasserini
 * @param <R> the type of the object model behind each row of the table.
 */
public class Table<R> extends Control {
	private Class<R> itemType;
	private List<Column<R>> columns = new ArrayList<Column<R>>();

	public Table(Container container) {
		super(container);
	}
	
	public Table(Container container, Class<R> itemType) {
		super(container);
		setItemType(itemType);
	}
	
	public void setItemType(Class<R> itemType) {
		this.itemType = itemType;
	}
	
	public Class<R> getItemType() {
		return itemType;
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	/**
	 * Asigna el contenido de esta tabla.
	 * 
	 * @param selected Nombre de la propiedad del modelo del contenedor desde la cuál se obtendrán los
	 *            contenidos de esta tabla
	 * @return Esta misma tabla, para enviar mensajes anidados
	 */
	public Binding<TableBuilder<?>> bindItemsToProperty(String propertyName) {
		return this.bindItems(new ObservableProperty(propertyName));
	}

	/**
	 * Asigna el contenido de esta tabla.
	 * 
	 * @param model Una característica observable del modelo.
	 * 
	 * @return Esta misma tabla, para enviar mensajes anidados
	 */
	public Binding<TableBuilder<?>> bindItems(Observable model) {
		return this.addBinding(model, new ObservableTableContents());
	}

	/**
	 * @deprecated Use {@link #bindSelectionToProperty(String)} instead
	 */
	@Deprecated
	public <C extends ControlBuilder> Binding<C> bindSelection(String selected) {
		return bindSelectionToProperty(selected);
	}

	/**
	 * Vincula la fila seleccionada de esta tabla con una propiedad del modelo asociado al contenedor.
	 * 
	 * This is just a synonym of {@link #bindValueToProperty(String)} 
	 * 
	 * @param selected Nombre de la propiedad contra la cual se desea vincular la fila seleccionada de esta
	 *            tabla.
	 * 
	 * @return Esta misma tabla, para enviar mensajes anidados
	 */
	public <C extends ControlBuilder> Binding<C> bindSelectionToProperty(String selected) {
		return this.bindValueToProperty(selected);
	}
	

	// ********************************************************
	// ** Columns
	// ********************************************************

	public Table<R> addColumn(Column<R> column) {
		this.columns.add(column);
		return this;
	}

	public List<Column<R>> getColumns() {
		return this.columns;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************

	/**
	 * @return Un {@link TableBuilder} que ya tiene agregadas las columnas de esta tabla.
	 */
	@Override
	protected TableBuilder<R> createBuilder(PanelBuilder container) {
		TableBuilder<R> tableBuilder = container.addTable(this.itemType);

		for (Column<R> column : this.columns) {
			column.showOn(tableBuilder);
		}

		return tableBuilder;
	}

	// ********************************************************
	// ** Deprecated
	// ********************************************************

	/**
	 * This method is retained only for backwards compatibility and will be removed in a future version.
	 * @deprecated Use {@link #bindItemsToProperty(String)}
	 */
	public Table<R> bindContentsToProperty(String propertyName) {
		this.bindItemsToProperty(propertyName);
		return this;
	}

	/**
	 * This method is retained only for backwards compatibility and will be removed in a future version.
	 * 
	 * @deprecated Use {@link #bindItemsToProperty(String)}
	 */
	public Table<R> bindContents(Observable model) {
		this.bindItems(model);
		return this;
	}

}
