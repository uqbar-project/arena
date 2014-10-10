package org.uqbar.arena.tests.labels;

import org.uqbar.arena.tests.ObjectWithProperty;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.lacar.ui.model.Action;

public class LabelsResize extends MainWindow<ObjectWithProperty> {

	private static final long serialVersionUID = 1L;

	public LabelsResize(ObjectWithProperty model) {
		super(model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		final ObjectWithProperty model = this.getModelObject();
		
		Label lbl = new Label(mainPanel);
		lbl.bindValueToProperty("nombre");
		
		new Button(mainPanel).onClick(new Action() {
			@Override
			public void execute() {
				model.setNombre("Soy un string largo y vivo en el bosque!!!");
			}
		}).bindCaptionToProperty("nombre");
	}
	
	public static void main(String[] args) {
		new LabelsResize(new ObjectWithProperty()).startApplication();
	}
}
