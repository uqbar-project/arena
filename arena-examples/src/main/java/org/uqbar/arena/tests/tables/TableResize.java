package org.uqbar.arena.tests.tables;

import org.uqbar.arena.tests.ObjectWithProperty;
import org.uqbar.arena.tests.Persona;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.lacar.ui.model.Action;

public class TableResize extends MainWindow<ObjectWithProperty> {

	private static final long serialVersionUID = 1L;

	public TableResize(ObjectWithProperty model) {
		super(model);
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		this.setMinHeight(200);
		
		final ObjectWithProperty model = this.getModelObject();
		 
		Table<Persona> tbl = new Table<Persona>(mainPanel, Persona.class);
		tbl.setNumberVisibleRows(5);
		tbl.bindItemsToProperty("personas");
		
	    new Column<Persona>(tbl)
	      .setTitle("Nombre")
	      .setFixedSize(150)
	      .bindContentsToProperty("nombre");

	    new Column<Persona>(tbl)
	      .setTitle("Apellido")
	      .setFixedSize(200)
	      .bindContentsToProperty("apellido");

	    new Column<Persona>(tbl)
	      .setTitle("Edad")
	      .setFixedSize(50)
	      .alignRight()
	      .bindContentsToProperty("edad");

		
		new Button(mainPanel).onClick(new Action() {
			@Override
			public void execute() {
				model.getPersonas().add(new Persona("Pepe", "Sanchez", 41));
				model.getPersonas().add(new Persona("Fabrizio", "Oberto", 38));
				model.getPersonas().add(new Persona("Hector", "Campana", 40));
				model.getPersonas().add(new Persona("Luis", "Lopez", 28));
				model.getPersonas().add(new Persona("Angel", "Lopez", 26));
			}
		}).setCaption("Llenar Tabla");
	}
	
	public static void main(String[] args) {
		new TableResize(new ObjectWithProperty()).startApplication();
	}
}
