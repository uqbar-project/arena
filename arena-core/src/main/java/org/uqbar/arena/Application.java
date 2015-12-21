package org.uqbar.arena;

import org.uqbar.aop.transaction.ObjectTransactionManager;
import org.uqbar.apo.APOClassLoader;
import org.uqbar.apo.APOConfig;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.utils.ReflectionUtils;
//import org.uqbar.lacar.ui.impl.jface.JFaceApplicationBuilder;
import org.uqbar.lacar.ui.model.ApplicationRunner;
import org.uqbar.lacar.ui.model.WindowFactory;

/**
 * Punto de entrada a una aplicación Arena.
 * 
 * @author npasserini
 */
public abstract class Application implements WindowOwner, Runnable {
	private ApplicationRunner delegate;
	private static final String APPLICATION_RUNNER_PROPERTY = "arena.applicationRunner";

	public Application() {
		this.delegate = ReflectionUtils.newInstanceForName(APOConfig.getProperty(APPLICATION_RUNNER_PROPERTY).value());
		this.validateClassLoader();
	}

	/**
	 * Se fija que se esté usando el class loader necesario
	 */
	protected void validateClassLoader() {
		if (!this.getClass().getClassLoader().getClass().getName().equals(this.getNecesaryClassLoaderName())) {
			throw new RuntimeException(
					"Esta aplicación no está corriendo con el ClassLoader necesario. Corra la aplicación con el siguiente parámetro para la VM: -Djava.system.class.loader="
							+ this.getNecesaryClassLoaderName()
							+ ". El ClassLoader actual es: "
							+ this.getClass().getClassLoader());
		}
	}

	/**
	 * Devuelve el nombre del classLoader que es capaz de cargar la aplicación
	 * correctamente
	 * 
	 * @return
	 */
	protected String getNecesaryClassLoaderName() {
		return APOClassLoader.class.getName();
	}

	/**
	 * Arranca la aplicación. Este es el único mensaje que debería mandarse a la
	 * aplicación, el resto es manejado por el framework.
	 */
	public void start() {
		this.delegate.run(this);
	}

	/**
	 * Este método debe ser sobreescrito por las aplicaciones concretas, para
	 * crear la ventana principal de la aplicación.
	 */
	protected abstract Window<?> createMainWindow();

	// ********************************************************
	// ** Internal
	// ********************************************************

	/**
	 * ATENCIÓN: Este método es para uso interno del framework y no debe ser
	 * invocado directamente ni redefinido.
	 */
	@Override
	public void run() {
		ObjectTransactionManager.getTransaction();
		this.createMainWindow().open();
	}

	/**
	 * ATENCIÓN: Este método es para uso interno del framework y no debe ser
	 * invocado directamente ni redefinido.
	 */
	@Override
	public WindowFactory getDelegate() {
		return this.delegate;
	}

}