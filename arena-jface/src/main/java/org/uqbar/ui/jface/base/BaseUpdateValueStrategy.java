package org.uqbar.ui.jface.base;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.internal.databinding.BindingMessages;
import org.eclipse.core.runtime.IStatus;

import org.uqbar.commons.model.UserException;

/**
 * 
 * @author jfernandes
 */
public class BaseUpdateValueStrategy extends UpdateValueStrategy {

	@Override
	protected IStatus doSet(IObservableValue observableValue, Object value) {
		try {
			// No se puede llamar a super porque atrapa las excepciones y nosotros queremos poder manejarlas
			// nosotros (para diferenciar la UserException).
			observableValue.setValue(value);
			return ValidationStatus.ok();
		}
		catch (UnsupportedOperationException ex) {
			// TODO Esto se produce porque el ObservableValue es read-only. Revisar cómo se maneja esto,
			// por ahora repito el código de la superclase pero no estoy convencido.
			// Atrapar la excepción así es equivalente a ignorarla porque el error no se va a mostrar en
			// ningún lado.
			return ValidationStatus.error(BindingMessages.getString("ValueBinding_ErrorWhileSettingValue"), ex);
		}
		catch (RuntimeException exception) {
			// Las excepciones nos llegan wrappeadas dentro de una runtime, por eso atrapamos todas y buscamos
			// si en la causa tienen alguna UserException.
			return ValidationStatus.error(UserException.find(exception).getMessage());
		}
	}
	
}
