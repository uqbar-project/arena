package org.uqbar.arena.tests.nestedCombos;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.List;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.RadioSelector;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.lacar.ui.model.Action;

import com.uqbar.commons.collections.Transformer;

/**
 * Examples on various {@link Selector} features.<br>
 * When a country is selected in the first {@link Selector}:
 * <ul>
 * <li>the elements in the {@link List} and second {@link Selector} should show the provinces of the selected
 * country.</li>
 * <li>the displayed country in the {@link Label} should show the selected country.</li>
 * <li>the number of times should be incremented (testing {@link Selector#onSelection(Action)}</li>
 * </ul>
 * 
 * All the elements in {@link Selector}s, {@link List}s and {@link Label}s should render nice Country or
 * Province names (instead of an ugly {@link #toString()}.
 * 
 * The {@link List} of provinces and the {@link Selector} of provinces should be synchronized. Selecting an
 * element in either of each should increment the last {@link Label} too (since they are synchronized, the
 * count is incremented twice.
 * 
 * @author npasserini
 */
public class NestedCombosWindow extends MainWindow<NestedCombosDomain> {
	public static void main(String[] args) {
		new NestedCombosWindow().startApplication();
	}

	public NestedCombosWindow() {
		super(new NestedCombosDomain());
	}

	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());

		Selector<Country> countries = new RadioSelector<Country>(mainPanel);
		countries.setContents(this.getModelObject().getPossibleCountries(), "name");

		PropertyAdapter nameAdapter = new PropertyAdapter(Country.class, "name");

		countries.bindItemsToProperty("possibleCountries") //
			.setAdapter(nameAdapter);

		countries.bindValueToProperty("country");
		
		Action changedAction = new Action() {
			@Override
			public void execute() {
				getModelObject().changed();
			}
		};

		countries.onSelection(changedAction);

		Label country = new Label(mainPanel);
		country.bindValueToProperty("country"); // TODO esto todavía no se puede hacer:
												// .setAdapter(nameAdapter);

		List<Province> provincesList = new List<Province>(mainPanel);
		provincesList.bindItemsToProperty("possibleProvinces") //
			.setAdapter(new PropertyAdapter(Province.class, "name"));
		provincesList.bindValueToProperty("province");
		provincesList.setHeigth(100);
		provincesList.setWidth(100);
		provincesList.onSelection(changedAction);

		Selector<Province> provinces = new Selector<Province>(mainPanel);
		provinces.bindItemsToProperty("possibleProvinces").setAdapter(new PropertyAdapter(Province.class, "name"));
		provinces.bindValueToProperty("province");
		provinces.onSelection(changedAction);

		Label times = new Label(mainPanel);
		times.bindValueToProperty("times"); // TODO esto todavía no se puede hacer: .setAdapter(nameAdapter);

		new TextBox(mainPanel).bindValueToProperty("province.name");
		
		new Button(mainPanel) //
			.setCaption("Edit Province")
			.onClick(new MessageSend(this, "editProvince"));
		
		new Button(mainPanel) //
			.setCaption("Delete Province")
			.onClick(new MessageSend(this.getModelObject(), "deleteProvince"));

		Table<Province> table = new Table<Province>(mainPanel, Province.class);
		table.setWidth(200);
		table.setHeigth(200);
		table.bindItemsToProperty("possibleProvinces");
		table.bindValueToProperty("province");

		new Column<Province>(table).setTitle("Property").bindContentsToProperty("name");
		new Column<Province>(table).setTitle("Transformer").bindContentsToTransformer(
			new Transformer<Province, String>() {
				@Override
				public String transform(Province element) {
					return element.getName();
				}
			});
	}
	
	public void editProvince() {
		new EditProvinceDialog(this, this.getModelObject().getProvince()).open();
	}
}
