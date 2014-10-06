package org.uqbar.arena.tests.tables;

import org.uqbar.arena.tests.ObjectWithProperty;
import org.uqbar.arena.tests.Persona;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.lacar.ui.model.Action;

public class TableWithoutSize extends MainWindow<ObjectWithProperty> {

	private static final long serialVersionUID = 1L;

	public TableWithoutSize(ObjectWithProperty model) {
		super(model);
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		this.setMinHeight(200);
		
		final ObjectWithProperty model = this.getModelObject();
		 
		Table<Persona> tbl = new Table<Persona>(mainPanel, Persona.class);
		tbl.bindItemsToProperty("personas");
		
	    new Column<Persona>(tbl)
	      .setTitle("Nombre")
	      .setFixedSize(200)
	      .bindContentsToProperty("nombre");

	    new Column<Persona>(tbl)
	      .setTitle("Apellido")
	      .setFixedSize(200)
	      .bindContentsToProperty("apellido");

		
		new Button(mainPanel).onClick(new Action() {
			@Override
			public void execute() {
				model.getPersonas().add(new Persona("Pepe", "Sanchez"));
				model.getPersonas().add(new Persona("Fabrizio", "Oberto"));
				model.getPersonas().add(new Persona("Hector", "Campana"));
				model.getPersonas().add(new Persona("Luis", "Lopez"));
				model.getPersonas().add(new Persona("Angel", "Lopez"));
			}
		}).setCaption("Llenar Tabla");
	}
	
	public static void main(String[] args) {
		new TableWithoutSize(new ObjectWithProperty()).startApplication();
	}
}
