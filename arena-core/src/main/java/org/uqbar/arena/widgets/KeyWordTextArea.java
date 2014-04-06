package org.uqbar.arena.widgets;

import java.util.HashMap;
import java.util.Map;

import org.uqbar.arena.widgets.style.Style;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.builder.traits.SkinnableBuilder;

public class KeyWordTextArea extends TextBox {
	private Map<String[], Style> configurationStyle = new HashMap<>();

	public KeyWordTextArea(Panel container) {
		super(container);
	}
	
	public Style keyWords(String... keyWords){
		Style style = new Style();
		configurationStyle.put(keyWords, style);
		return style;
	}
	
	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		SkinnableBuilder textBox = container.addStyleTextArea(configurationStyle);
		this.configureSkineableBuilder(textBox);
		return textBox;
	}

}
