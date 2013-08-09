package org.uqbar.arena.bindings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.uqbar.commons.model.UserException;

import com.uqbar.commons.StringUtils;

/**
 * Convierte a Date los Strings en formato dd/MM/yyy. Acepta valores nulos.
 * 
 * @see SimpleDateFormat
 * @author npasserini
 */
public final class DateAdapter implements Transformer<Date, String> {
	public String pattern = "dd/MM/yyyy";

	@Override
	public Date viewToModel(String valueFromView) {
		try {
			return StringUtils.isBlank(valueFromView) ? null : new SimpleDateFormat(pattern).parse(valueFromView);
		}
		catch (ParseException e) {
			throw new UserException("Debe INGRESAR una fecha en formato: " + this.pattern);
		}
	}

	@Override
	public String modelToView(Date valueFromModel) {
		return new SimpleDateFormat(pattern).format(valueFromModel);
	}
	
	@Override
	public Class<Date> getModelType() {
		return Date.class;
	}

	@Override
	public Class<String> getViewType() {
		return String.class;
	}
}
