package org.uqbar.ui.jface.base.converters;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.core.databinding.conversion.IConverter;

public class ArenaStringToBigDecimalConverter implements IConverter {

	@Override
	public Object getFromType() {
		return String.class;
	}

	@Override
	public Object getToType() {
		return BigDecimal.class;
	}

	@Override
	public Object convert(Object fromObject) {
		try {
			String from = (String) fromObject;
			NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
			Number parsed = formatter.parse(from);
	        return new BigDecimal(parsed.toString());
		} catch (ParseException e) {
			return null;
		} catch (ClassCastException e) {
			return fromObject;
		}
	}

}
