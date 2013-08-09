package org.uqbar.arena.tests.PropertyNotFound;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;


public class PropertyNotFoundWindow extends MainWindow<Object> {
	public PropertyNotFoundWindow(Object model) {
		super(model);
	}
	
	public static void main(String[] args) {
		new PropertyNotFoundWindow(new Object()).startApplication();
	}

	@Override
	public void createContents(Panel mainPanel) {
		TextBox t = new TextBox(mainPanel);
		t.bindValueToProperty("noExisto");
	}
}
