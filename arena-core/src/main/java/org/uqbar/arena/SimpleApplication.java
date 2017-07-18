package org.uqbar.arena;

import java.lang.reflect.Constructor;

import org.apache.commons.beanutils.ConstructorUtils;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.model.ApplicationRunner;

/**
 * Simple app implementation to work with MainWindows.
 * We still need to have this and cannot create the app on-the-fly from the window itself
 * because we need to go through the {@link ApplicationRunner} first so it can perform
 * initialization tasks like setting jface realm and creating a display.
 * Otherwise the window tries to create a model object which could be using
 * some jfacedatabinding observables that won't get any Realm exploding
 * 
 * 
 * @author jfernandes
 */
public class SimpleApplication extends Application {
	private Class<? extends Window<?>> classOfWindow;

	public SimpleApplication(Class<? extends Window<?>> classOfWindow) {
		this.classOfWindow = classOfWindow;
	}

	@Override
	protected Window<?> createMainWindow() {
		try {
			Constructor constructor = ConstructorUtils.getAccessibleConstructor(classOfWindow, WindowOwner.class);
			return (Window<?>) constructor.newInstance(this);
		} catch (Exception e) {
			throw new ArenaException("La clase de la ventana debe tener un constructor con un unico parametro de tipo 'WindowOwner'", e);
		}
	}
	
	public static void start(Class<? extends Window<?>> classOfWindow) {
		new SimpleApplication(classOfWindow).start();
	}

}
