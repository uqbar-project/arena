package org.uqbar.arena.bootstrap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.arena.filters.NumericFilter;

public class TestNumericFilter {

	NumericFilter filterWithDecimals;
	NumericFilter filterInteger;
	
	@Before
	public void init() {
		filterWithDecimals = new NumericFilter();
		filterInteger = new NumericFilter(false);
	}
	
	@Test
	public void testPositiveNumber() {
		Assert.assertTrue(filterWithDecimals.isNumeric("120"));
	}
	
	@Test
	public void testZeroNumber() {
		Assert.assertTrue(filterWithDecimals.isNumeric("0"));
	}

	@Test
	public void testZeroStringNumber() {
		Assert.assertFalse(filterWithDecimals.isNumeric(""));
	}

	@Test
	public void testAlphabeticDigitIsNotANumber() {
		Assert.assertFalse(filterWithDecimals.isNumeric("A"));
	}

	@Test
	public void testFirstAlphabeticDigitIsNotANumber() {
		Assert.assertFalse(filterWithDecimals.isNumeric("A11"));
	}

	@Test
	public void testLastAlphabeticDigitIsNotANumber() {
		Assert.assertFalse(filterWithDecimals.isNumeric("11A"));
	}

	@Test
	public void testASpaceBetweenNumberFailsToConvertAsNumber() {
		Assert.assertFalse(filterWithDecimals.isNumeric("1 1"));
	}

	@Test
	public void testDecimalCommaNumber() {
		Assert.assertTrue(filterWithDecimals.isNumeric("3,3"));
	}

	@Test
	public void testDecimalPointNumber() {
		Assert.assertTrue(filterWithDecimals.isNumeric("3.3"));
	}

	@Test
	public void testNegativeNumber() {
		Assert.assertTrue(filterWithDecimals.isNumeric("-33"));
	}

	@Test
	public void testNegativeDecimalCommaNumber() {
		Assert.assertTrue(filterWithDecimals.isNumeric("-3,3"));
	}

	@Test
	public void testNegativeDecimalPointNumber() {
		Assert.assertTrue(filterWithDecimals.isNumeric("-3.3"));
	}

	@Test
	public void testInvalidNegativeNumber() {
		Assert.assertFalse(filterWithDecimals.isNumeric("-3AU"));
	}

	@Test
	public void testInvalidNumber() {
		Assert.assertFalse(filterWithDecimals.isNumeric("/44"));
	}

	@Test
	public void testNull() {
		Assert.assertFalse(filterWithDecimals.isNumeric(null));
	}

	@Test
	public void testIntegerOk() {
		Assert.assertTrue(filterInteger.isNumeric("20"));
	}

	@Test
	public void testIntegerNotOkBecauseOfComma() {
		Assert.assertFalse(filterInteger.isNumeric("20,4"));
	}
	
	@Test
	public void testIntegerNotOkBecauseOfPoint() {
		Assert.assertFalse(filterInteger.isNumeric("20.4"));
	}
	
	@Test
	public void testIntegerNotOkBecauseOfChar() {
		Assert.assertFalse(filterInteger.isNumeric("20A4"));
	}

	@Test
	public void testNegativeIntegerOk() {
		Assert.assertTrue(filterInteger.isNumeric("-20"));
	}

	@Test
	public void testNegativeIntegerNotOkBecauseOfComma() {
		Assert.assertFalse(filterInteger.isNumeric("-20,4"));
	}
	
	@Test
	public void testNegativeIntegerNotOkBecauseOfPoint() {
		Assert.assertFalse(filterInteger.isNumeric("-20.4"));
	}
	
	@Test
	public void testNegativeIntegerNotOkBecauseOfChar() {
		Assert.assertFalse(filterInteger.isNumeric("-20A4"));
	}

}
