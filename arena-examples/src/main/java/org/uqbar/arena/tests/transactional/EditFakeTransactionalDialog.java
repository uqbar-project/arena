package org.uqbar.arena.tests.transactional;

import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.WindowOwner;

public class EditFakeTransactionalDialog extends TransactionalDialog<FakeObject>{

	private static final long serialVersionUID = 1L;

	public EditFakeTransactionalDialog(WindowOwner owner, FakeObject model) {
		super(owner, model);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		new TextBox(mainPanel).bindValueToProperty("name");
		new Label(mainPanel).bindValueToProperty("name");
		
		new Button(mainPanel).setCaption("save").onClick(() -> this.accept());
		
	}

}
