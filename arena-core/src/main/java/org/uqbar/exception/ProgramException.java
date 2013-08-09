package org.uqbar.exception;

@SuppressWarnings("serial")
public class ProgramException extends RuntimeException {

	public ProgramException(Throwable e) {
		super(e);
	}
	
	public ProgramException(String msg, Throwable e) {
		super(msg, e);
	}

	public ProgramException(String msg) {
		super(msg);
	}
	
}
