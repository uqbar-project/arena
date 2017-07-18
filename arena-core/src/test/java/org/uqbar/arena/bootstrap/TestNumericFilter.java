package org.uqbar.arena.bootstrap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.arena.filters.NumericFilter;

public class TestNumericFilter {

	NumericFilter filter;
	
	@Before
	public void init() {
		filter = new NumericFilter();
	}
	
	@Test
	public void testPositiveNumber() {
		Assert.assertTrue(filter.isNumeric("120"));
	}
	
	@Test
	public void testZeroNumber() {
		Assert.assertTrue(filter.isNumeric("0"));
	}

	@Test
	public void testZeroStringNumber() {
		Assert.assertFalse(filter.isNumeric(""));
	}

	@Test
	public void testAlphabeticDigitIsNotANumber() {
		Assert.assertFalse(filter.isNumeric("A"));
	}

	@Test
	public void testFirstAlphabeticDigitIsNotANumber() {
		Assert.assertFalse(filter.isNumeric("A11"));
	}

	@Test
	public void testLastAlphabeticDigitIsNotANumber() {
		Assert.assertFalse(filter.isNumeric("11A"));
	}

	@Test
	public void testASpaceBetweenNumberFailsToConvertAsNumber() {
		Assert.assertFalse(filter.isNumeric("1 1"));
	}

	@Test
	public void testDecimalCommaNumber() {
		Assert.assertTrue(filter.isNumeric("3,3"));
	}

	@Test
	public void testDecimalPointNumber() {
		Assert.assertTrue(filter.isNumeric("3.3"));
	}

	@Test
	public void testNegativeNumber() {
		Assert.assertTrue(filter.isNumeric("-33"));
	}

	@Test
	public void testNegativeDecimalCommaNumber() {
		Assert.assertTrue(filter.isNumeric("-3,3"));
	}

	@Test
	public void testNegativeDecimalPointNumber() {
		Assert.assertTrue(filter.isNumeric("-3.3"));
	}

	@Test
	public void testInvalidNegativeNumber() {
		Assert.assertFalse(filter.isNumeric("-3AU"));
	}

	@Test
	public void testInvalidNumber() {
		Assert.assertFalse(filter.isNumeric("/44"));
	}

}
