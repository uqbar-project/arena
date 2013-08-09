package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;

import org.uqbar.lacar.ui.impl.jface.windows.JFaceWindowBuilder;
import org.uqbar.lacar.ui.model.ApplicationRunner;
import org.uqbar.lacar.ui.model.WindowBuilder;

/**
 * Implementacion de {@link ApplicationRunner} para jface.
 * 
 * @author npasserini
 */
public class JFaceApplicationBuilder implements ApplicationRunner {
	private Display display;

	public JFaceApplicationBuilder() {
		this.display = new Display();
	}

	@Override
	public void run(Runnable application) {
		Realm.runWithDefault(SWTObservables.getRealm(this.display), application);
		this.display.dispose();
	}

	@Override
	public WindowBuilder createWindow() {
		return new JFaceWindowBuilder();
	}

}
