package org.uqbar.ui.jface.base.converters;

import java.math.BigDecimal;

public class ArenaBigDecimalToStringConverter extends ArenaNumberToStringConverter {

	@Override
	public Object getFromType() {
		return BigDecimal.class;
	}

	public double getConvertedValue(Object fromObject) {
		BigDecimal from = (BigDecimal) fromObject;
		return from.doubleValue();
	}

}
