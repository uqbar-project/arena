package org.uqbar.lacar.ui.impl.jface.tables;

import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.viewers.TableViewer;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory;
import org.uqbar.lacar.ui.impl.jface.lists.JFaceItemsBindingBuilder;

/**
 * Creates bindings for the items of viewer with columns, such as {@link TableViewer}
 * 
 * @author npasserini
 */
public class JFaceTableItemsBindingBuilder<Row> extends JFaceItemsBindingBuilder {
	private IObservableSet itemsObservableSet;
	private final JFaceTableBuilder<Row> table;

	public JFaceTableItemsBindingBuilder(JFaceTableBuilder<Row> table) {
		super(table.getJFaceTableViewer());
		this.table = table;
	}

	@Override
	public void observeProperty(Object modelObject, String propertyName) {
		super.observeProperty(modelObject, propertyName);
		this.itemsObservableSet = JFaceObservableFactory.observeSet(modelObject, propertyName);
	}

	@Override
	public void build() {
		// TODO Review: If we removed the responsibility of creating the label providr from this object, we
		// could avoid to have an explicit reference to the TableBuilder
		this.getViewer().setLabelProvider(
			new JFaceLabelProviderBuilder<Row>(this.table, this.itemsObservableSet).createLabelProvider());

		// Warning: Label provider must be set before setting the input (which will be done in super.build);
		super.build();
	}
}
