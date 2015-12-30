package org.uqbar.ui.jface.base.converters;

public class ArenaDoubleToStringConverter extends ArenaNumberToStringConverter {

	@Override
	public Object getFromType() {
		return Double.class;
	}

	@Override
	public double getConvertedValue(Object fromObject) {
		Double value = (Double) fromObject;
		return value;
	}

}
