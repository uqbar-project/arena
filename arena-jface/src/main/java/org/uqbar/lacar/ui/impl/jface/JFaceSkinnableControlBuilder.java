package org.uqbar.lacar.ui.impl.jface;

import java.awt.Color;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;
import org.uqbar.lacar.ui.model.SkinnableBuilder;


public abstract class JFaceSkinnableControlBuilder<T extends Control> extends JFaceControlBuilder<T>  implements SkinnableBuilder{
	

	public JFaceSkinnableControlBuilder(JFaceContainer container) {
		super(container);
	}
	
	public JFaceSkinnableControlBuilder(JFaceContainer container, T jfaceWidget) {
		super(container, jfaceWidget);
	}
	
	
	@Override
	public void setForeground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = getSWTColor(color);
		this.getWidget().setForeground(swtColor);
	}

	
	@Override
	public void setBackground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = getSWTColor(color);
		this.getWidget().setBackground(swtColor);
	}
	
	@Override
	public void setFontSize(int size) {
		FontData[] fontData = this.getWidget().getFont().getFontData();
		for(int i = 0; i < fontData.length; ++i)
		    fontData[i].setHeight(size);

		final Font newFont = new Font(this.getWidget().getDisplay(), fontData);
		this.getWidget().setFont(newFont);
		
		this.getWidget().getFont();
	}
	
	protected org.eclipse.swt.graphics.Color getSWTColor(Color color) {
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(getWidget().getDisplay(), red, green, blue);
		return swtColor;
	}

}
