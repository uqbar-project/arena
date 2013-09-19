package org.uqbar.arena.examples.controls;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;

/**
 * Ejemplo simple de un control de tipo {@link Table}.
 * Tiene como modelo un objeto de tipo {@link Factura} (de compras)
 * y muestra los items {@link ItemFactura}.
 * 
 * @author jfernandes
 */
public class TableExampleWindow extends MainWindow<Factura> {

	public TableExampleWindow() {
		super(createModel());
	}

	protected static Factura createModel() {
		Factura factura = new Factura();
		factura.addItem(new ItemFactura("Coca Cola Zero 500cc", 3));
		factura.addItem(new ItemFactura("Pepitos", 1));
		return factura;
	}

	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new ColumnLayout(1));
		
		Table<ItemFactura> table = new Table<ItemFactura>(mainPanel, ItemFactura.class);
		table.bindItemsToProperty("items");
		
		this.addColumns(table);
		
		new Label(mainPanel).setText("Nombre:");
		new TextBox(mainPanel).bindValueToProperty("nombre");
		new Button(mainPanel).bindCaptionToProperty("nombre");
	}

	protected void addColumns(Table<ItemFactura> table) {
		Column<ItemFactura> productoColumn = new Column<ItemFactura>(table);
		productoColumn.setTitle("Producto");
		productoColumn.setFixedSize(180);
		productoColumn.bindContentsToProperty("producto");
		
		Column<ItemFactura> cantidadColumn = new Column<ItemFactura>(table);
		cantidadColumn.setTitle("Cantidad");
		//cantidadColumn.setFixedSize();
		cantidadColumn.bindContentsToProperty("cantidad");
	}

	public static void main(String[] args) {
		new TableExampleWindow().startApplication();
	}
	
}
