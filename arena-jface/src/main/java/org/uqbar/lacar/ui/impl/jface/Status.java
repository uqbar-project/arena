package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.IStatus;

/**
 * Representa el estado de una tarea, adaptado para su uso en la interfaz de usuario, mostrar los errores de
 * validación, etc.
 * 
 * @author npasserini
 */
public class Status implements IStatus {
	// TODO Revisar el diseño de esta clase
	private AggregateValidationStatus status;

	public Status(DataBindingContext dbc) {
		this.status = new AggregateValidationStatus(dbc, AggregateValidationStatus.MAX_SEVERITY);
	}

	public AggregateValidationStatus getAggregateValidationStatus() {
		return this.status;
	}

	private IStatus getInternalStatus() {
		return (IStatus) this.getAggregateValidationStatus().getValue();
	}

	@Override
	public IStatus[] getChildren() {
		return this.getInternalStatus().getChildren();
	}

	@Override
	public int getCode() {
		return this.getInternalStatus().getCode();
	}

	@Override
	public Throwable getException() {
		return this.getInternalStatus().getException();
	}

	@Override
	public String getMessage() {
		return this.getInternalStatus().getMessage();
	}

	@Override
	public String getPlugin() {
		return this.getInternalStatus().getPlugin();
	}

	@Override
	public int getSeverity() {
		return this.getInternalStatus().getSeverity();
	}

	@Override
	public boolean isMultiStatus() {
		return this.getInternalStatus().isMultiStatus();
	}

	@Override
	public boolean isOK() {
		return this.getInternalStatus().isOK();
	}

	@Override
	public boolean matches(int arg0) {
		return this.getInternalStatus().matches(arg0);
	}

}
