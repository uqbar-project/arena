package org.uqbar.arena.examples.controls.binding.enable;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;

/**
 * 
 * @author jfernandes
 */
public class BindEnabledExampleWindow extends MainWindow<SAdress> {
	private static final long serialVersionUID = 1L;

	public BindEnabledExampleWindow() {
		super(new SAdress());
	}

	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		
//		new Label(mainPanel).setText("Country:");
//		new Selector<Country>(mainPanel)
//			.setContents(SAdress.createCountries(), "name")
//			.bindValueToProperty("country")
//			.setAdapter(new PropertyAdapter(Country.class, "name"));
		
		Selector<Country> selector = new Selector<Country>(mainPanel);

		selector//
			.allowNull(false)//
			.bindValueToProperty("country");

		selector//
		.bindItems(//
				new ObservableProperty(new SAdress(), "countries"))
//			.setContents(SAdress.createCountries(), "")
			.setAdapter(new PropertyAdapter(Country.class, "name"));
		
		new Label(mainPanel).setText("State:");
		TextBox state = new TextBox(mainPanel);
		state.bindValueToProperty("state");
		state.bindEnabledToProperty("country").notNull();
		
		new Label(mainPanel).setText("Street:");
		TextBox street = new TextBox(mainPanel);
		street.bindValueToProperty("street");
		street.bindEnabledToProperty("state").notEmpty();
	}
	
	public static void main(String[] args) {
		new BindEnabledExampleWindow().startApplication();
	}

}
