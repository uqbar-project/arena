package org.uqbar.arena.windows;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * 
 * @author npasserini
 */
public class ErrorsPanel extends Widget {
	private String okMessage;
	private int preferredLines = 1;

	public ErrorsPanel(Panel container, String okMessage) {
		super(container);
		this.okMessage = okMessage;
	}

	public ErrorsPanel(Panel container, String okMessage, int preferredLines) {
		super(container);
		this.okMessage = okMessage;
		this.preferredLines = preferredLines;
	}

	@Override
	public void showOn(PanelBuilder container) {
		container.addErrorPanel(this.okMessage, this.preferredLines);
	}

}