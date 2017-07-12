package org.uqbar.arena.examples.controls.binding.enable;

import org.uqbar.arena.examples.controls.FacturaNoObservable;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;

public class FacturaWindow extends MainWindow<FacturaNoObservable> {

	public FacturaWindow(FacturaNoObservable model) {
		super(model);
	}

	private static final long serialVersionUID = -3097255784937110820L;

	@Override
	public void createContents(Panel mainPanel) {
		new Label(mainPanel).setText("Saldo:");
		TextBox saldo = new TextBox(mainPanel);
		saldo.bindValueToProperty("saldo");
	}

	public static void main(String[] args) {
		new FacturaWindow(new FacturaNoObservable()).startApplication();
	}
}
