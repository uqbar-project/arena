package org.uqbar.ui.jface.controller;

import java.util.Collection;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * 
 * @author npasserini
 */
public class OneToManyContentProvider implements IStructuredContentProvider {
	private IObservableValue observableValue;
	private String propertyName;

	public OneToManyContentProvider(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Collection<?> value = (Collection<?>) this.observableValue.getValue();
		return value != null ? value.toArray() : new Object[]{};
	}

	@Override
	public void inputChanged(final Viewer viewer, Object oldInput, Object newInput) {
		// Dispose old value observer
		this.dispose();

		// Create new observer and listen to its changes.
		if (newInput != null) {
			this.observableValue = BeansObservables.observeValue(newInput, this.propertyName);
			this.observableValue.addValueChangeListener(new IValueChangeListener() {
				@Override
				public void handleValueChange(ValueChangeEvent event) {
					viewer.refresh();
				}
			});
		}
		else {
			this.observableValue = null;
		}
	}

	@Override
	public void dispose() {
		if (this.observableValue != null) {
			this.observableValue.dispose();
		}
	}
}