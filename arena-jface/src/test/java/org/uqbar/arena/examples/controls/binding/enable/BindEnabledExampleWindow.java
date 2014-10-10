package org.uqbar.arena.examples.controls.binding.enable;

import org.uqbar.arena.SimpleApplication;
import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.List;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

/**
 * 
 * @author jfernandes
 */
public class BindEnabledExampleWindow extends Window<InputAddress> {
	private static final long serialVersionUID = 1L;

	public BindEnabledExampleWindow(WindowOwner parent) {
		super(parent, new InputAddress());
	}

	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Country:");
		
		Selector<Country> selector = new List<Country>(mainPanel);
		selector //
			.allowNull(false) //
			.bindValueToProperty("country");

		selector
			.bindItemsToProperty("countries")
			.setAdapter(new PropertyAdapter(Country.class, "name"));
		
		new Label(mainPanel).setText("State:");
		TextBox state = new TextBox(mainPanel);
		state.bindValueToProperty("state");
		state.bindEnabledToProperty("country").notNull();
		
		new Label(mainPanel).setText("Street:");
		TextBox street = new TextBox(mainPanel);
		street.bindValueToProperty("street");
		street.bindEnabledToProperty("state").notEmpty();
		
		new Button(mainPanel).setCaption("Add Country").onClick(new MessageSend(getModelObject(), "addCountry"));
		new Button(mainPanel).setCaption("Remove Country").onClick(new MessageSend(getModelObject(), "removeSelectedCountry"));
	}
	
	public static void main(String[] args) {
		SimpleApplication.start(BindEnabledExampleWindow.class);
	}

}
