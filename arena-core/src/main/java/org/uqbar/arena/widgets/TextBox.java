package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.SkinnableBuilder;
import org.uqbar.lacar.ui.model.TextControlBuilder;

import com.uqbar.commons.collections.Closure;

/**
 * Control que permite editar texto simple.
 * 
 * @author npasserini
 */
public class TextBox extends SkinnableControl {
	private static final long serialVersionUID = 1L;

	public TextBox(Panel container) {
		super(container);
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		SkinnableBuilder textBox = container.addTextBox();
		this.configureSkineableBuilder(textBox);
		return textBox;
	}
	
	public TextBox withFilter(final TextFilter filter) {
		this.addConfiguration(new Closure<TextControlBuilder>() {
			@Override
			public void execute(TextControlBuilder builder) {
				builder.addTextFilter(filter);
			}
		});
		return this;
	}

}
