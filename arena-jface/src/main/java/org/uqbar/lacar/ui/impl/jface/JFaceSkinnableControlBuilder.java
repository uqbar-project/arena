package org.uqbar.lacar.ui.impl.jface;

import java.awt.Color;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.builder.traits.SkinnableBuilder;
import org.uqbar.ui.swt.utils.SWTUtils;

public abstract class JFaceSkinnableControlBuilder<T extends Control> extends JFaceControlBuilder<T>  implements SkinnableBuilder{

	public JFaceSkinnableControlBuilder(JFaceContainer container) {
		super(container);
	}
	
	public JFaceSkinnableControlBuilder(JFaceContainer container, T jfaceWidget) {
		super(container, jfaceWidget);
	}
	
//	@Override
	public BindingBuilder observeBackground() {
		return new JFaceBindingBuilder(this, SWTObservables.observeBackground(getWidget()));
	}
		
	@Override
	public void setForeground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = SWTUtils.getSWTColor(this.getWidget().getDisplay(), color);
		this.getWidget().setForeground(swtColor);
	}

	
	@Override
	public void setBackground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = SWTUtils.getSWTColor(this.getWidget().getDisplay(), color);
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
}
