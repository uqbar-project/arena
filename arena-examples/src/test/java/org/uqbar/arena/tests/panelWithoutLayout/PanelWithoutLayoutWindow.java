package org.uqbar.arena.tests.panelWithoutLayout;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.GroupPanel;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.MainWindow;

public class PanelWithoutLayoutWindow extends MainWindow<PanelWithoutLayoutDomain> {

	public PanelWithoutLayoutWindow(PanelWithoutLayoutDomain model) {
		super(model);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void createContents(Panel mainPanel) {
		GroupPanel innerPanel = new GroupPanel(mainPanel);
		innerPanel.setTitle("Buttons");
		innerPanel.setLayout(new HorizontalLayout());
		
		Button b = new Button(innerPanel);
		Button b2 = new Button(innerPanel);

		b.setCaption("Ok");
		b2.setCaption("Ok2");
	}	
	
	public static void main(String[] args) {
		new PanelWithoutLayoutWindow(new PanelWithoutLayoutDomain()).startApplication();
	}
}
