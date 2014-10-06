package org.uqbar.arena.widgets.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.ObservableValue;
import org.uqbar.arena.widgets.Container;
import org.uqbar.arena.widgets.Control;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.TableBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

import com.uqbar.commons.collections.Closure;

/**
 * 
 * 
 * @author npasserini
 * @param <R> the type of the object model behind each row of the table.
 */
public class Table<R> extends Control {
	private static final long serialVersionUID = 1L;
	private Class<R> itemType;
	private List<Column<R>> columns = new ArrayList<Column<R>>();

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Binding<Collection<R>,Table<R>, TableBuilder<R>> bindItemsToProperty(String propertyName) {
		return this.bindItems(new ObservableProperty(propertyName));
	}

	/**
	 * Asigna el contenido de esta tabla.
	 * 
	 * @param model Una característica observable del modelo.
	 * 
	 * @return Esta misma tabla, para enviar mensajes anidados
	 */
	// type-safe: should be an Observable<? extends Collection>
//	M debería ser +M con covariante (?)
	@SuppressWarnings("unchecked")
	public <M> Binding<Collection<R>, Table<R>, TableBuilder<R>> bindItems(Observable<M> model) {
		return (Binding<Collection<R>, Table<R>, TableBuilder<R>>) this.addBinding(model, items());
	}
	
	public ViewObservable<Table<R>, TableBuilder<R>> items() {
		return new ObservableTableContents<R>(this);
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
	public <C extends ControlBuilder> Binding<R, Control, C> bindSelectionToProperty(String selected) {
		return this.bindValueToProperty(selected);
	}
	
	public ObservableValue<Control, ControlBuilder> selection() {
		return this.value();
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

	public Table<R> setNumberVisibleRows(final int numberVisibleRows) {
		this.configurations.add(new Closure<WidgetBuilder>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void execute(WidgetBuilder builder) {
				((TableBuilder)builder).setNumberVisibleRows(numberVisibleRows);
			}
		});
		return this;
	}
	
}
