package org.uqbar.arena.widgets.style;

import java.awt.Color;

public class Style {
	public final static int ITALIC = 1 << 1;
	public final static int BOLD = 1 << 0;

	private Color backgound;
	private Color foreground;
	private int fontStyle;
	private boolean underline;
	
	public Color backgound() {
		return backgound;
	}
	public Style backgound(Color backgound) {
		this.backgound = backgound;
		return this;
	}
	public Color foreground() {
		return foreground;
	}
	public Style foreground(Color foreground) {
		this.foreground = foreground;
		return this;
	}
	public int fontStyle() {
		return fontStyle;
	}
	public Style fontStyle(int fontStyle) {
		this.fontStyle = this.fontStyle | fontStyle;
		return this;
	}
	public boolean underline() {
		return underline;
	}
	public Style underline(boolean underline) {
		this.underline = underline;
		return this;
	}
}
