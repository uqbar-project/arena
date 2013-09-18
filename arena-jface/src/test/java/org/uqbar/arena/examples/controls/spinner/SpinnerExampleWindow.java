package org.uqbar.arena.examples.controls.spinner;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Spinner;
import org.uqbar.arena.windows.MainWindow;

/**
 * Example to show the {@link Spinner} arena control.
 * 
 * @see Spinner
 * 
 * @author jfernandes
 */
public class SpinnerExampleWindow extends MainWindow<Alarm> {

	public SpinnerExampleWindow() {
		super(new Alarm());
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		
		// defcon (without annotation)
		new Label(mainPanel).setText("DefCon:");
		Spinner defConSpinner = new Spinner(mainPanel);
		defConSpinner.setMaximumValue(5);
		defConSpinner.setMinimumValue(1);
		defConSpinner.bindValueToProperty("defCon");
		
		
		// annotatedDefCon (with annotations, no need to specify the min and max values in the view)
		new Label(mainPanel).setText("DefCon:");
		Spinner annotatedDefConSpinner = new Spinner(mainPanel);
		annotatedDefConSpinner.bindValueToProperty("annotatedDefCon");
		
	}
	
	public static void main(String[] args) {
		new SpinnerExampleWindow().startApplication();
	}

}
