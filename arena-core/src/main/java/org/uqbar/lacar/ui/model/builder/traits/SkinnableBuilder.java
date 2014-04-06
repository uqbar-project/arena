package org.uqbar.lacar.ui.model.builder.traits;

import java.awt.Color;

import org.uqbar.lacar.ui.model.ControlBuilder;

public interface SkinnableBuilder extends ControlBuilder {
	
	void setForeground(Color color);
	
	void setBackground(Color color);
	
	void setFontSize(int size);

}
