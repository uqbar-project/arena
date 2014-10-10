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

	public ErrorsPanel(Panel container, String okMessage) {
		super(container);
		this.okMessage = okMessage;
	}

	@Override
	public void showOn(PanelBuilder container) {
		container.addErrorPanel(this.okMessage);
	}

}