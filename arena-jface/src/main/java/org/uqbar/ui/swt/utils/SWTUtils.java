package org.uqbar.ui.swt.utils;

import org.eclipse.swt.SWT;

/**
 * Holds utility functions to work with SWT.
 * 
 * @author jfernandes
 */
public class SWTUtils {
	
	public static boolean isLeftSet(int style) {
		return isFlagSet(style, SWT.LEFT);
	}
	
	public static boolean isCenterSet(int style) {
		return isFlagSet(style, SWT.CENTER);
	}
	
	public static boolean isRightSet(int style) {
		return isFlagSet(style, SWT.RIGHT);
	}
	
	public static boolean isFlagSet(int style, int flag) {
		return (style & flag) != 0;
	}

}
