package org.uqbar.arena.tests.nestedPropertyAccess;

import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.tests.nestedCombos.Country;
import org.uqbar.arena.tests.nestedCombos.NestedCombosDomain;
import org.uqbar.arena.tests.nestedCombos.Province;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.MainWindow;

public class NestedPropertyAccessWindow extends MainWindow<NestedCombosDomain> {

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static void main(String[] args) {
		new NestedPropertyAccessWindow(new NestedCombosDomain()).startApplication();
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public NestedPropertyAccessWindow(NestedCombosDomain model) {
		super(model);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void createContents(Panel mainPanel) {
		Selector<Country> countries = new Selector<Country>(mainPanel);
		countries.bindItemsToProperty("possibleCountries").setAdapter(new PropertyAdapter(Country.class, "name"));
		countries.bindValueToProperty("country");

		Selector<Province> provinces = new Selector<Province>(mainPanel);
		provinces.bindItemsToProperty("country.provinces").setAdapter(new PropertyAdapter(Province.class, "name"));
	}
}