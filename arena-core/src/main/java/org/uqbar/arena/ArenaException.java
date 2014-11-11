package org.uqbar.arena;

/**
 * Base class for all arena exceptions
 * 
 * @author jfernandes
 */
public class ArenaException extends RuntimeException {

	private static final long serialVersionUID = -2612636878670001518L;

	public ArenaException(Exception cause) {
		super(cause);
	}

	public ArenaException(String message, Exception cause) {
		super(message, cause);
	}

	public ArenaException(String message) {
		super(message);
	}

}
