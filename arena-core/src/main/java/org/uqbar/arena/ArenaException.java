package org.uqbar.arena;

import com.uqbar.commons.exceptions.ProgramException;

/**
 * Base class for all arena exceptions
 * 
 * @author jfernandes
 */
public class ArenaException extends ProgramException {

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
