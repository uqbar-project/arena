package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public final class ObservableErrorPanelForegroundColor extends ComputedValue {
	private AggregateValidationStatus status;

	public ObservableErrorPanelForegroundColor(AggregateValidationStatus status) {
		this.status = status;
	}

	@Override
	protected Object calculate() {
		IStatus currentStatus = (IStatus) this.status.getValue();
		return currentStatus.isOK() //
			? Display.getCurrent().getSystemColor(SWT.COLOR_BLACK)
			: Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	}
}