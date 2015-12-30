package org.uqbar.ui.jface.base.converters;

public class ArenaFloatToStringConverter extends ArenaNumberToStringConverter {

	@Override
	public Object getFromType() {
		return Float.class;
	}

	@Override
	public double getConvertedValue(Object fromObject) {
		Float value = (Float) fromObject;
		return value.doubleValue();
	}

}
