package org.uqbar.arena.tests.tables;

import org.uqbar.arena.tests.Persona;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.ErrorsPanel;
import org.uqbar.arena.windows.MainWindow;

public class ErrorsPanelWindow extends MainWindow<Persona> {

	public ErrorsPanelWindow(Persona model) {
		super(model);
	}

	private static final long serialVersionUID = 2929979059674370552L;

	@Override
	public void createContents(Panel mainPanel) {
		setMinHeight(500);
		setMinWidth(500);
		new ErrorsPanel(mainPanel, "Estamos listos para comenzar. Estamos listos para comenzar.\n", 4);
		new TextBox(mainPanel).bindValueToProperty("edad");
	}

	public static void main(String[] args) {
		Persona erico = new Persona("Heriberto", "Lavallen", 43);
		new ErrorsPanelWindow(erico).startApplication();
	}
}
