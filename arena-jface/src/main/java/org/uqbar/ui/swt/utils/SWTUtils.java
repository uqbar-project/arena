package org.uqbar.ui.swt.utils;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

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
	
	public static  org.eclipse.swt.graphics.Color getSWTColor(Display display, Color color) {
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(display, red, green, blue);
		return swtColor;
	}

}
