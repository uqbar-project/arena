package org.uqbar.arena.tests.transactional;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.MainWindow;

public class TransactionalFireEventWindow extends MainWindow<FakeObject> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -76404121229915381L;

	public static void main(String[] args) {
		new TransactionalFireEventWindow().startApplication();
	}

	public TransactionalFireEventWindow() {
		super(new FakeObject("Soy un Fake"));
	}

	@Override
	public void createContents(Panel mainPanel) {
		new Label(mainPanel).bindValueToProperty("name");
		new Button(mainPanel).setCaption("Edit").onClick(() -> this.openEditor());
	}
	
	public void openEditor(){
		new EditFakeTransactionalDialog(this, this.getModelObject()).open();
	}
}
