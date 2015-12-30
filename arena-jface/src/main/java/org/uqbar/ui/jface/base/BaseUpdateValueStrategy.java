package org.uqbar.ui.jface.base;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.internal.databinding.BindingMessages;
import org.eclipse.core.internal.databinding.Pair;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.uqbar.commons.model.UserException;
import org.uqbar.ui.jface.base.converters.ArenaBigDecimalToStringConverter;
import org.uqbar.ui.jface.base.converters.ArenaDoubleToStringConverter;
import org.uqbar.ui.jface.base.converters.ArenaFloatToStringConverter;
import org.uqbar.ui.jface.base.converters.ArenaIntegerToStringConverter;
import org.uqbar.ui.jface.base.converters.ArenaStringToBigDecimalConverter;

/**
 * 
 * @author jfernandes
 */
public class BaseUpdateValueStrategy extends UpdateValueStrategy {

	private HashMap<Pair, IConverter> converters = null;

	@Override
	protected IStatus doSet(IObservableValue observableValue, Object value) {
		try {
			// No se puede llamar a super porque atrapa las excepciones y nosotros queremos poder manejarlas
			// para diferenciar la UserException
			observableValue.setValue(value);
			
			if(observableValue instanceof AbstractSWTObservableValue){
				AbstractSWTObservableValue ov = (AbstractSWTObservableValue) observableValue;
				
                if(ov.getWidget() instanceof Control){
                    Control c = (Control) ov.getWidget();
				    Composite p = c.getParent();
				    while(p!=null){
					    p.layout();
					    p = p.getParent();
				    }
                }
			}
			
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

	/**
	 * Hook method para meterle un conversor nuevo que implemente IConverter,
	 * pero que no tome las conversiones locas para integers, bigdecimals, etc.
	 * 
	 */
	protected IConverter createConverter(Object fromType, Object toType) {
		IConverter overridedConverter = getConvertersMap().get(new Pair(fromType, toType));
		if (overridedConverter != null) {
			return overridedConverter;
		}
		return super.createConverter(fromType, toType);
	}

	private Map<Pair, IConverter> getConvertersMap() {
		if (converters == null) {
			converters = new HashMap<Pair, IConverter>();
			converters.put(new Pair(Integer.class, String.class), new ArenaIntegerToStringConverter());
			converters.put(new Pair(Integer.TYPE, String.class), new ArenaIntegerToStringConverter());
			converters.put(new Pair(Double.class, String.class), new ArenaDoubleToStringConverter());
			converters.put(new Pair(Double.TYPE, String.class), new ArenaDoubleToStringConverter());
			converters.put(new Pair(Float.class, String.class), new ArenaFloatToStringConverter());
			converters.put(new Pair(Float.TYPE, String.class), new ArenaFloatToStringConverter());
			converters.put(new Pair(BigDecimal.class, String.class), new ArenaBigDecimalToStringConverter());
			converters.put(new Pair(String.class, BigDecimal.class), new ArenaStringToBigDecimalConverter());
		}
		return converters;
	}
	
}
