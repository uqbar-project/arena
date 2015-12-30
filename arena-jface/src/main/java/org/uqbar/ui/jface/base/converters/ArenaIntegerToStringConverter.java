package org.uqbar.ui.jface.base.converters;

import org.eclipse.core.databinding.conversion.IConverter;

public class ArenaIntegerToStringConverter implements IConverter {

	@Override
	public Object getFromType() {
		return Integer.class;
	}

	@Override
	public Object getToType() {
		return String.class;
	}

	@Override
	public Object convert(Object fromObject) {
		if (fromObject == null) {
			return "";
		}
		return String.valueOf(fromObject);
	}

}
