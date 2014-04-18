package org.uqbar.arena.widgets.tables;

import java.awt.Color;
import java.util.List;

import org.uqbar.lacar.ui.model.ColumnBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;
import org.uqbar.lacar.ui.model.TableBuilder;

import com.uqbar.commons.collections.Closure;
import com.uqbar.commons.collections.CollectionFactory;
import com.uqbar.commons.collections.Transformer;

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
	private List<LabelProvider<R>> labelProvider = CollectionFactory.createList();
	private List<Closure<ColumnBuilder<R>>> configurations = CollectionFactory.createList();

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
	
	public <U> Column<R> bindBackground(final String propertyName, Transformer<U, Color> transformer) {
		this.labelProvider.add(new BackgoundProvider<R, U, Color>(propertyName, transformer));
		return this;
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
