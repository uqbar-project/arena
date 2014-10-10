package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.runtime.IStatus;

public class ObservableStatusMessage extends ComputedValue {
	private final AggregateValidationStatus status;
	private final String okMessage;

	public ObservableStatusMessage(AggregateValidationStatus status, String okMessage) {
		this.status = status;
		this.okMessage = okMessage;
	}

	@Override
	protected Object calculate() {
		IStatus currentStatus = (IStatus) this.status.getValue();
		return currentStatus.isOK() ? this.okMessage : currentStatus.getMessage();
	}
}
