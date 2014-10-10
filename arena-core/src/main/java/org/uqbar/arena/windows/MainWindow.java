package org.uqbar.arena.windows;

import org.uqbar.arena.Application;

/**
 * Window especial para aplicaciones sencillas con una sola ventana.
 * Simulan al objeto {@link Application}
 * 
 * @author npasserini
 */
public abstract class MainWindow<T> extends Window<T> {
	private Application application;

	public MainWindow(T model) {
		super(null, model);
		this.application = new Application() {
			@Override
			protected Window<?> createMainWindow() {
				return MainWindow.this;
			}
		};
	}

	@Override
	public WindowOwner getOwner() {
		return this.application;
	}

	public void startApplication() {
		this.application.start();
	}
}
