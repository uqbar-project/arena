package org.uqbar.common.transaction;

/**
 * Exception for errors generated in POT
 * 
 * @author flbulgarelli
 * @since 3.5
 */
public class ObjectTransactionException extends RuntimeException {

	private static final long serialVersionUID = 586950998028185929L;

	public ObjectTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectTransactionException(String message) {
		super(message);
	}
}
