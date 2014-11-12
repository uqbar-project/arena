package org.uqbar.arena.widgets.tables;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.widgets.tables.labelprovider.BackgroundProvider;
import org.uqbar.arena.widgets.tables.labelprovider.ForegroundProvider;
import org.uqbar.arena.widgets.tables.labelprovider.PropertyLabelProvider;
import org.uqbar.arena.widgets.tables.labelprovider.TransformerLabelProvider;
import org.uqbar.lacar.ui.model.ColumnBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;
import org.uqbar.lacar.ui.model.TableBuilder;
import org.apache.commons.collections15.Closure;

import org.apache.commons.collections15.Transformer;

/**
 * Una columna de una tabla.
 * 
 * Es obligatorio configurar los contenidos de la columna utilizando {@link #bindContentsToProperty(String)} o
 * {@link #bindContentsToTransformer(Transformer)}
 * 
 * @param <R> El tipo de los objetos que se muestran en la tabla en la que se agrega esta columna. Cada uno de
 *            estos objetos estará asociado a una fila de la tabla.
 * 
 * @author npasserini
 */
public class Column<R> {
	private List<LabelProvider<R>> labelProvider = new ArrayList<>();
	private List<Closure<ColumnBuilder<R>>> configurations = new ArrayList<>();

	public Column(Table<R> table) {
		table.addColumn(this);
	}

	// ********************************************************
	// ** Column configuration
	// ********************************************************

	public Column<R> setTitle(final String title) {
		this.configurations.add(new Closure<ColumnBuilder<R>>() {
			@Override
			public void execute(ColumnBuilder<R> builder) {
				builder.setTitle(title);
			}
		});
		return this;
	}

	public Column<R> setWeight(final int preferredSize) {
		this.configurations.add(new Closure<ColumnBuilder<R>>() {
			@Override
			public void execute(ColumnBuilder<R> builder) {
				builder.setWeight(preferredSize);
			}
		});
		return this;
	}
	
	public Column<R> setFixedSize(final int pixels) {
		this.configurations.add(new Closure<ColumnBuilder<R>>() {
			@Override
			public void execute(ColumnBuilder<R> builder) {
				builder.setFixedSize(pixels);
			}
		});
		return this;
	}
	
	public Column<R> setBackground(final Color color) {
		this.configurations.add(new Closure<ColumnBuilder<R>>() {
			@Override
			public void execute(ColumnBuilder<R> builder) {
				builder.setBackground(color);
			}
		});
		return this;
	}
	
	public Column<R> setForeground(final Color color) {
		this.configurations.add(new Closure<ColumnBuilder<R>>() {
			@Override
			public void execute(ColumnBuilder<R> builder) {
				builder.setForeground(color);
			}
		});
		return this;
	}
	
	public Column<R> setFont(final int size) {
		this.configurations.add(new Closure<ColumnBuilder<R>>() {
			@Override
			public void execute(ColumnBuilder<R> builder) {
				builder.setFontSize(size);
			}
		});
		
		return this;
	}
	

	// ********************************************************
	// ** Binding
	// ********************************************************

	public PropertyLabelProvider<R> bindContentsToProperty(String propertyName) {
		PropertyLabelProvider<R> label = new PropertyLabelProvider<R>(propertyName);
		this.labelProvider.add(label);
		return label;
	}

	/**
	 * @deprecated usar {@link #bindContentsToProperty(String)} y luego llamar a setTransformer
	 * sobre el objeto que devuelve. De esa forma no se pierde el binding a la property.
	 * Claro que ese transformer solo se puede usar para transformar el valor de la propiedad.
	 * No recibe por parámetro al objeto modelo de la fila, sino el valor de la property.
	 */
	public <U> Column<R> bindContentsToTransformer(Transformer<R, U> transformer) {
		this.labelProvider.add(new TransformerLabelProvider<R, U>(transformer));
		return this;
	}
	
	public BackgroundProvider<R, Color> bindBackground(final String propertyName) {
		BackgroundProvider<R, Color> label = new BackgroundProvider<R,  Color>(propertyName);
		this.labelProvider.add(label);
		return label;
	}
	
	public ForegroundProvider<R, Color> bindForeground(final String propertyName) {
		ForegroundProvider<R, Color> label = new ForegroundProvider<R, Color>(propertyName);
		this.labelProvider.add(label);
		return label;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************

	public void showOn(TableBuilder<R> table) {
		ColumnBuilder<R> builder = table.addColumn(this.labelProvider);

		for (Closure<ColumnBuilder<R>> configuration : this.configurations) {
			configuration.execute(builder);
		}
	}
}
