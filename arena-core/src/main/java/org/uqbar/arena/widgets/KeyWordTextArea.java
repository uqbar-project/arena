package org.uqbar.arena.widgets;

import java.util.ArrayList;
import java.util.List;

import org.scalatest.Entry;
import org.uqbar.arena.widgets.style.Style;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.SkinnableBuilder;

public class KeyWordTextArea extends TextBox{
	
	private List<Entry<String[], Style>> configurationStyle = new ArrayList<>();

	public KeyWordTextArea(Panel container) {
		super(container);
	}
	
	public Style keyWords(String... keyWords){
		Style style = new Style();
		configurationStyle.add(new Entry<String[], Style>(keyWords, style));
		return style;
	}
	
	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		SkinnableBuilder textBox = container.addStyleTextArea(configurationStyle);
		this.configureSkineableBuilder(textBox);
		return textBox;
	}

}
