package org.uqbar.common.transaction;


/**
 * @author jfernandes
 * @since 3.5
 */
public class ObjectTransactionAssertions {

	public static void assertNull(String message, Object obj) {
		assertTrue(message, obj == null);
	}

	public static void assertNotNull(String message, Object obj) {
		assertTrue(message, obj != null);
	}

	public static void assertTrue(String message, boolean condition) {
		if (!condition) {
			throw new ObjectTransactionException(message);
		}
	}

	public static void assertArgumentNotNull(String message, Object argument) {
		assertNotNull(message, argument);
	}

}
