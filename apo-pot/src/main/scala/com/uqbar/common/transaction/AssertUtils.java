package com.uqbar.common.transaction;

import com.uqbar.commons.exceptions.ProgramException;

/**
 * 
 * @author jfernandes
 */
public class AssertUtils {

	public static void assertNull(String message, Object obj) {
		assertTrue(message, obj == null);
	}

	public static void assertNotNull(String message, Object obj) {
		assertTrue(message, obj != null);
	}

	public static void assertTrue(String message, boolean condition) {
		if (!condition) {
			throw new ProgramException(message);			
		}
	}

	public static void assertArgumentNotNull(String message, Object argument) {
		assertNotNull(message, argument);
	}

}
