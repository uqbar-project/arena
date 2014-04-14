package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.TextControlBuilder;
import org.uqbar.lacar.ui.model.builder.StyledControlBuilder;
import org.uqbar.lacar.ui.model.builder.traits.StyledWidgetBuilder;

import com.uqbar.commons.collections.Closure;

/**
 * Control que permite editar texto simple.
 * 
 * @author npasserini
 */
public class TextBox extends SkinnableControl {
	private static final long serialVersionUID = 1L;
	private boolean multiLine = false;

	public TextBox(Panel container) {
		super(container);
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		StyledControlBuilder textBox = container.addTextBox(multiLine);
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
	
	public TextBox selectFinalLine() {
		this.addConfiguration(new Closure<TextControlBuilder>() {
			@Override
			public void execute(TextControlBuilder builder) {
				builder.selectFinalLine();
			}
		});
		return this;
	}

	public boolean isMultiLine() {
		return multiLine;
	}

	public TextBox setMultiLine(boolean multiLine) {
		this.multiLine = multiLine;
		return this;
	}

}
