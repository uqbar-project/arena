package org.uqbar.ui.jface.base.converters;

import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.core.databinding.conversion.IConverter;

public abstract class ArenaNumberToStringConverter implements IConverter {

	abstract public Object getFromType();

	@Override
	public Object getToType() {
		return String.class;
	}

	@Override
	public Object convert(Object fromObject) {
		try {
			NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
			formatter.setGroupingUsed(false);
			return formatter.format(getConvertedValue(fromObject));
		} catch (ClassCastException e) {
			return fromObject.toString();
		}
	}

	abstract public double getConvertedValue(Object fromObject);

}
