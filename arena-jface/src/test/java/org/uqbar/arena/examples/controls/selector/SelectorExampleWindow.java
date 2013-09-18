package org.uqbar.arena.examples.controls.selector;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.MainWindow;

/**
 * Ejemplo de uso del control Selector.
 * 
 * @author jfernandes
 */
public class SelectorExampleWindow extends MainWindow<OrdenDeRestaurant> {

	public SelectorExampleWindow() {
		super(new OrdenDeRestaurant());
	}

	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Entrada:");
		new Selector<Entrada>(mainPanel)
			// setContents(lista de entradas, propiedad a mostrar de la Entrada) 
			.setContents(Restaurant.getInstance().getEntradas(), "descripcion")
			.bindValueToProperty("entrada");
		
		new Label(mainPanel).setText("Plato:");
		new Selector<Plato>(mainPanel)
			.setContents(Restaurant.getInstance().getPlatos(), "nombreDePlato")
			.bindValueToProperty("plato");
		
		new Label(mainPanel).setText("Bebida:");
		new Selector<Bebida>(mainPanel)
			.setContents(Restaurant.getInstance().getBebidas(), "nombreDeBebida")
			.bindValueToProperty("bebida");
	}
	
	public static void main(String[] args) {
		new SelectorExampleWindow().startApplication();
	}

}