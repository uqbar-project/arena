package org.uqbar.lacar.ui.impl.jface.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.uqbar.arena.widgets.tables.LabelProviderBuilder;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory;
import org.uqbar.lacar.ui.impl.jface.bindings.JavaBeanTransacionalObservableMap;
import org.uqbar.lacar.ui.impl.jface.builder.tables.JFaceColumnBuilder;
import org.uqbar.lacar.ui.impl.jface.builder.tables.JFaceTableBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

import com.uqbar.commons.collections.Transformer;

/**
 * @param <R> El tipo de las columnas de la tabla a configurar.
 * 
 * @author npasserini
 */
public class JFaceLabelProviderBuilder<R> implements LabelProviderBuilder<R> {
	private List<String> columnPropertyNames = new ArrayList<String>();
	private int columnIndex = 0;
	private int delegatedColumnIndex = 0;
	private Map<Integer, Transformer<R, ?>> calculatedColumns = new HashMap<Integer, Transformer<R, ?>>();
	private Map<Integer, Transformer<Object, ?>> calculatedBackgroundColumns = new HashMap<>();
	private final JFaceTableBuilder<R> table;
	private ColumnsLabelProvider<R> columnsLabelProvider;
	private final IObservableSet tableContents;
	private Map<Integer, JavaBeanTransacionalObservableMap> observeBackgounds = new HashMap<>();

	public JFaceLabelProviderBuilder(JFaceTableBuilder<R> table, IObservableSet tableContents) {
		this.table = table;
		this.tableContents = tableContents;
		this.columnsLabelProvider = new ColumnsLabelProvider<R>();
	}

	public IBaseLabelProvider createLabelProvider() {
		for (JFaceColumnBuilder<R> column : this.table.columns()) {
			List<LabelProvider<R>>  labelProviders = column.labelProviders();
			if (labelProviders.isEmpty()) {
				throw new RuntimeException("Column without label provider");
			}
			
			for (LabelProvider<R> labelProvider : labelProviders) {
				labelProvider.configure(this);
			}
		}

		IObservableMap[] attributeMaps = JFaceObservableFactory.observeMaps(tableContents, this.table.itemType(),
			this.columnPropertyNames.toArray(new String[this.columnPropertyNames.size()]));

		ObservableMapProvider decorated = new ObservableMapProvider(attributeMaps);
		decorated.initializeBackground(calculatedBackgroundColumns, observeBackgounds);
		decorated.widget_$eq(table.getWidget());
		this.columnsLabelProvider.initialize(decorated, this.calculatedColumns);
		return this.columnsLabelProvider;
		// return new ObservableMapLabelProvider(attributeMaps);
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	@Override
	public void addPropertyMappedColumn(String propertyName) {
		this.columnPropertyNames.add(propertyName);
		final int index = this.delegatedColumnIndex++;
		this.addCalculatedColumn(this.columnsLabelProvider.getDelegatingTransformer(index));
	}

	@Override
	public void addCalculatedColumn(Transformer<R, ?> transformer) {
		this.calculatedColumns.put(this.columnIndex++, transformer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void observeBackgoundColumn(String propertyName, Transformer<?, ?> transformer) {
		observeBackgounds.put(this.columnIndex-1, JFaceObservableFactory.observeMap(tableContents, this.table.itemType(), propertyName));
		this.calculatedBackgroundColumns.put(this.columnIndex-1, (Transformer<Object, ?>) transformer);
	}

}