package com.uqbar.commons.loggeable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * DOCME:
 * 
 * @author npasserini
 * @author jfernandes
 */
public class HierarchicalLogger {
	protected StringBuffer buffer = new StringBuffer();
	private int currentIndentLevel = 0;

	public static String hierarchicalToString(Loggeable loggeable) {
		HierarchicalLogger visitor = new HierarchicalLogger();
		loggeable.appendYourselfTo(visitor);

		return visitor.toString();
	}

	// ****************************
	// ** DATA APPENDS
	// ****************************

	public void appendAll(Map additionalData) {
		for (Iterator iter = additionalData.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			this.append(entry.getKey().toString(), entry.getValue());
		}
	}

	public void appendAll(Collection additionalData) {
		/*
		 * if(additionalData.isEmpty()) { //todo esto tendria sentido si no hubiera pasado por el metodo que
		 * agrega el nombre, etc. this.appendEmptyCollection(); }
		 */
		/*
		 * if (additionalData.size() == 1) { //this.appendCollectionStartNode(additionalData);
		 * //this.appendObject(additionalData.iterator().next());
		 * //this.appendCollectionEndNode(additionalData); } else {
		 */
		for (Iterator iter = additionalData.iterator(); iter.hasNext();) {
			Object element = iter.next();
			this.append(null, element);
		}
		// }
	}

	/**
	 * Appends the loggeable object to this info.
	 */
	public void append(Loggeable loggeable) {
		loggeable.appendYourselfTo(this);
	}

	public HierarchicalLogger append(String name, Object value) {
		this.currentIndentLevel++;

		try {
			this.buffer.append("\n");

			for (int i = 0; i < this.currentIndentLevel; i++) {
				this.buffer.append("\t");
			}
			this.appendInitialNode(name, value);
			this.appendObject(value);
			this.appendCloseNode(name, value);

			return this;
		}
		finally {
			this.currentIndentLevel--;
		}
	}

	// REFACTORME nombre como los de collection
	protected void appendInitialNode(String name, Object value) {
		if (name != null) {
			this.buffer.append(name);
			this.buffer.append(": ");
		}
	}

	protected void appendCloseNode(String name, Object value) {
		// NOTHING TO DO;
	}

	/**
	 * @author jfernandes
	 */
	public HierarchicalLogger append(String name, long value) {
		return this.append(name, new Long(value));
	}

	public HierarchicalLogger append(String name, boolean value) {
		return this.append(name, new Boolean(value));
	}

	public void append(String stringToAppend) {
		this.buffer.append(stringToAppend);
	}

	public void append(Class clazz) {
		this.append(clazz.getSimpleName());
	}

	protected void appendObject(Object objectToAppend) {
		try {
			if (objectToAppend == null) {
				this.append("null");
			}
			else if (objectToAppend instanceof Loggeable) {
				Loggeable hierarchicalToStringEnabled = (Loggeable) objectToAppend;
				this.append(hierarchicalToStringEnabled);
			}
			else if (objectToAppend instanceof Collection) {
				this.appendAll((Collection) objectToAppend);
			}
			else if (objectToAppend instanceof Map) {
				this.appendAll((Map) objectToAppend);
			}
			else if (objectToAppend instanceof Class) {
				this.append((Class) objectToAppend);
			}
			else if (objectToAppend instanceof Object[]) {
				this.appendAll(Arrays.asList((Object[]) objectToAppend));
			}
			else {
				this.append(objectToAppend.toString());
			}

			if (this.buffer.charAt(this.buffer.length() - 1) == '\n') {
				this.buffer.deleteCharAt(this.buffer.length() - 1);
			}
		}
		catch (RuntimeException exception) {
			logExceptionOnSystemError(objectToAppend, exception);
		}
	}

	private void logExceptionOnSystemError(Object objectToAppend, RuntimeException exception) {
		String errorMessage;
		if (objectToAppend == null) {
			errorMessage = "Could not log null object.";
		}
		else {
			errorMessage = "Could not log object of class: '" + objectToAppend.getClass() + "'.";
		}
		System.err.println(errorMessage);
		System.err.println("\nStacktrace:\n");
		exception.printStackTrace(System.err);
	}

	@Override
	public String toString() {
		return this.buffer.toString();
	}
}
