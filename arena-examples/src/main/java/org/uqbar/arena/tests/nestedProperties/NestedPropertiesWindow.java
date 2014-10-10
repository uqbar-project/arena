package org.uqbar.arena.tests.nestedProperties;

import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;

public class NestedPropertiesWindow extends MainWindow<NestedPropertiesModel> {
	public static void main(String[] args) {
		NestedPropertiesModel model = new NestedPropertiesModel();
		model.getList().add(new Nested("hola"));
		model.getList().add(new Nested("chau"));
		new NestedPropertiesWindow(model).startApplication();
	}

	public NestedPropertiesWindow(NestedPropertiesModel model) {
		super(model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		Selector<Nested> selector = new Selector<Nested>(mainPanel);
		selector.bindItemsToProperty("list").setAdapter(new PropertyAdapter(Nested.class, "second"));
		selector.bindValueToProperty("first");
		
		new TextBox(mainPanel).bindValueToProperty("second");
		new TextBox(mainPanel).bindValueToProperty("first.second");
	}
}
