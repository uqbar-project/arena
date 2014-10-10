package org.uqbar.arena.widgets;

import org.uqbar.commons.model.IModel;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * A special panel that has a border and a title.
 * 
 * @author jfernandes
 */
public class GroupPanel extends Panel {
	private String title;

	public GroupPanel(Container container) {
		super(container);
	}

	public GroupPanel(Container container, IModel<?> model) {
		super(container, model);
	}

	public GroupPanel(Container container, Object model) {
		super(container, model);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	protected PanelBuilder createPanel(PanelBuilder container) {
		return container.addChildGroup(this.title);
	}

}
