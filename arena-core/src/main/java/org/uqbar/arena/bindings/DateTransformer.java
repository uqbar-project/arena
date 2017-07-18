package org.uqbar.arena.bindings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.uqbar.commons.model.exceptions.UserException;

/**
 * Convierte a Date los Strings en formato dd/MM/yyy. Acepta valores nulos.
 * 
 * @see SimpleDateFormat
 * @author npasserini
 */
public final class DateTransformer implements ValueTransformer<Date, String> {
	public String pattern = "dd/MM/yyyy";

	@Override
	public Date viewToModel(String valueFromView) {
		try {
			return StringUtils.isBlank(valueFromView) ? null : new SimpleDateFormat(pattern).parse(valueFromView);
		}
		catch (ParseException e) {
			throw new UserException("Debe ingresar una fecha en formato: " + this.pattern);
		}
	}

	@Override
	public String modelToView(Date valueFromModel) {
		if (valueFromModel == null) {
			return null;
		}
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
