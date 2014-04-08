package org.uqbar.ui.jface.controller;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * @deprecated Esto no se está usando.Parece que antes se usaba desde JFaceTableBuilder 
 * @author npasserini
 */
@Deprecated
public class TableObservableValue extends AbstractObservableValue {
	private final Table table;
	private boolean updating = false;

	private Object currentValue;

	private SelectionListener listener;
	private Object itemType;

	public TableObservableValue(Table table, Class<?> itemType) {
		this.table = table;
		this.itemType = itemType;
		this.currentValue = doGetValue();

		if ((table.getStyle() & SWT.MULTI) > 0) {
			throw new IllegalArgumentException("SWT.SINGLE support only for a List selection"); //$NON-NLS-1$
		}

		this.listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!TableObservableValue.this.updating) {
					Object oldValue = TableObservableValue.this.currentValue;
					TableObservableValue.this.currentValue =  doGetValue();
					fireValueChange(Diffs.createValueDiff(oldValue, TableObservableValue.this.currentValue));
				}
			}
		};
		table.addSelectionListener(this.listener);
	}

	@Override
	public void doSetValue(Object value) {
		TableItem oldValue = null;
		if (this.table.getSelection() != null && this.table.getSelection().length > 0) {
			oldValue = this.table.getSelection()[0];
		}
		try {
			this.updating = true;
			TableItem[] items = this.table.getItems();
			int index = -1;
			if (items != null && value != null) {
				for (int i = 0; i < items.length; i++) {
					if (value.equals(items[i])) {
						index = i;
						break;
					}
				}
				this.table.select(index); // -1 will not "unselect"
			}
			if (value != null) {
				if(value instanceof TableItem){
					this.currentValue =  ((TableItem) value).getData();
				}else{
					this.currentValue = value;
				}
			}
		}
		finally {
			this.updating = false;
		}
		fireValueChange(Diffs.createValueDiff(oldValue, value));
	}

	@Override
	public Object doGetValue() {
		int index = this.table.getSelectionIndex();
		if (index >= 0) {
			return this.table.getItem(index).getData();
		}
		return null;
	}

	@Override
	public Object getValueType() {
		return this.itemType;
	}

	/**
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#dispose()
	 */
	@Override
	public synchronized void dispose() {
		super.dispose();
		if (this.listener != null && !this.table.isDisposed()) {
			this.table.removeSelectionListener(this.listener);
		}
	}
}
