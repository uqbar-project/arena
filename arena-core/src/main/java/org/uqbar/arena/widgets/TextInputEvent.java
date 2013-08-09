package org.uqbar.arena.widgets;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.uqbar.commons.StringUtils;
import com.uqbar.commons.exceptions.ProgramException;

/**
 * An event object that represents the action performed by the user on a {@link TextBox} control
 * to input text.
 * 
 * The text may be added at any position and maybe replacing part of the current text.
 * 
 * @author jfernandes
 */
public class TextInputEvent {
	private int startPosition;
	private int endPosition;
	private String inputText;
	// this shouldn't be a future, but I don't want to couple this
	// api with jface.
	// i should introduce an interface + jfaceimpl for this
	private Callable<String> currentText;
	
	public TextInputEvent(int startPosition, int endPosition, String inputText, Callable<String> currentText) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.inputText = inputText;
		this.currentText = currentText;
	}
	
	public TextInputEvent(int startPosition, int endPosition, String holaMundo, final String currentText) {
		this(startPosition, endPosition, holaMundo, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return currentText;
			}
		});
	}

	public String getInputText() {
		return this.inputText;
	}
	
	public String getCurrentText() {
		try {
			return this.currentText.call();
		} catch (InterruptedException e) {
			throw new ProgramException("Error while getting the current text", e);
		} catch (ExecutionException e) {
			throw new ProgramException("Error while getting the current text", e);	
		} catch (Exception e) {
			throw new ProgramException("Error while getting the current text", e);
		}
	}
	
	public int getStartPosition() {
		return this.startPosition;
	}
	
	public int getEndPosition() {
		return this.endPosition;
	}
	
	public String getPotentialTextResult() {
		String current = this.getCurrentText();
		return current.substring(0, this.startPosition) + this.getInputText() + current.substring(this.endPosition);
	}
	

}
