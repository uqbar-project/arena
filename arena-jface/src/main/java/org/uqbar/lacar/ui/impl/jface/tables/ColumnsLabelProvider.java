package org.uqbar.lacar.ui.impl.jface.tables;

import java.util.Map;

import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.uqbar.commons.collections.Transformer;

/**
 * @author npasserini
 */
public class ColumnsLabelProvider<R> extends ColumnLabelProvider implements ITableLabelProvider, ITableColorProvider{
	private ObservableMapProvider decorated;
	private Map<Integer, Transformer<R, ?>> calculatedColumns;

	/**
	 * Crea un {@link ColumnsLabelProvider} sin inicializar, debe llamarse a
	 * {@link #initialize(ObservableMapLabelProvider, Map)} antes de utilizarlo, en caso contrario se obtendrá
	 * una {@link NullPointerException}.
	 */
	public ColumnsLabelProvider() {
	}

	/**
	 * Crea un {@link ColumnsLabelProvider} ya inicializado y listo para ser utilizado.
	 */
	public ColumnsLabelProvider(ObservableMapProvider decorated, Map<Integer, Transformer<R, ?>> calculatedColumns) {
		this.initialize(decorated, calculatedColumns);
	}

	/**
	 * Inicializa este {@link ColumnsLabelProvider}. Luego de invocar a este método puede ser utilizado.
	 */
	public void initialize(ObservableMapProvider decorated, Map<Integer, Transformer<R, ?>> calculatedColumns) {
		this.decorated = decorated;
		this.calculatedColumns = calculatedColumns;
	}

	// ********************************************************
	// ** Cálculo del texto de una columma
	// ********************************************************

	@Override
	@SuppressWarnings("unchecked")
	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex >= this.calculatedColumns.size()) {
			// by default show as string
			return element.toString();
		}
		Object converted = this.calculatedColumns.get(columnIndex).transform((R) element);
		return converted != null ? converted.toString() : "";
	}

	public Transformer<R, Object> getDelegatingTransformer(final int columnIndex) {
		return new Transformer<R, Object>() {
			@Override
			public Object transform(R element) {
				return ColumnsLabelProvider.this.decorated.getColumnText(element, columnIndex);
			}
		};
	}

	// ********************************************************
	// ** Decorated methods
	// ********************************************************

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return this.decorated.getColumnImage(element, columnIndex);
	}

	@Override
	public String getText(Object element) {
		return this.decorated.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		return this.decorated.getImage(element);
	}
	
	public Color getBackground(Object element) {
		return this.decorated.getBackground(element);
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return this.decorated.isLabelProperty(element, property);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		this.decorated.addListener(listener);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		this.decorated.removeListener(listener);
	}

	@Override
	public void dispose() {
		this.decorated.dispose();
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return this.decorated.getForeground(element, columnIndex);
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		return this.decorated.getBackground(element, columnIndex);
	}
}
