package org.uqbar.arena.windows;

import java.util.List;

import org.uqbar.arena.widgets.Container;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Widget;
import org.uqbar.commons.model.IModel;
import org.uqbar.commons.model.Model;
import org.uqbar.commons.model.UserException;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.ViewDescriptor;
import org.uqbar.lacar.ui.model.WindowBuilder;

import com.uqbar.commons.collections.CollectionFactory;
import com.uqbar.commons.loggeable.HierarchicalLogger;
import com.uqbar.commons.loggeable.Loggeable;

/**
 * Superclase de todas las ventanas.
 * 
 * @param <T> El tipo del modelo de esta ventana
 * 
 * @author npasserini
 */
public abstract class Window<T> implements Container, ViewDescriptor<PanelBuilder>, WindowOwner, Loggeable {
	/**
	 * Puede ser la ventana padre o bien la aplicación en caso de que esta sea una ventana de primer nivel.
	 */
	private WindowOwner owner;

	/**
	 * @see #getDelegate()
	 */
	private WindowBuilder delegate;

	/**
	 * Objeto del modelo (de dominio o de aplicación) asociado a esta ventana.
	 */
	private IModel<T> model;

	/**
	 * El conjunto de los componentes que dependen <i>directamente</i> de la ventana. Normalmente esto
	 * consiste únicamente de un {@link Panel}.
	 */
	// TODO Debería haber una jerarquía de componentes que admiten hijos.
	private List<Widget> children = CollectionFactory.createList();

	private String title = "";
	
	private String iconImage;

	/**
	 * Indica si los contenidos de la ventana ya han sido creados, para evitar crearlos nuevamente.
	 * 
	 * Para explicitar el momento en el que se desea crear los contenidos se debe invocar a
	 * {@link #createContents()}, en caso de no hacerlo, los contenidos serán creados en el momento de abrir
	 * la ventana (mediante {@link #open()}).
	 */
	private boolean contentsReady;

	private int minHeight;

	@SuppressWarnings("unchecked")
	public Window(WindowOwner owner, T model) {
		if (model == null ){
			throw new UserException("The window does not have a  model assigned");
		}
		this.owner = owner;
		this.model = model instanceof IModel ? (IModel<T>) model : new Model<T>(model);
	}
	

	@Override
	public IModel<T> getModel() {
		return this.model;
	}

	public T getModelObject() {
		return this.model.getSource();
	}
	
	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}

	// ********************************************************
	// ** Configuración de la ventana
	// ********************************************************

	public Window<T> setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Invoca la creación de contenidos de la ventana. Las subclases no deberían tener necesidad de
	 * sobreescribir este método, en cambio se debe sobreescribir {@link #createContents(Panel)}.
	 * 
	 * Si no se invoca en ningún momento a este método, los contenidos serán creados en el momento de abrir la
	 * ventana en forma lazy. Este manejo permite crear los contenidos a mano en caso de ser necesario, para
	 * poder permitir a las subclases controlar el momento en que se hace y de esa forma realizar acciones
	 * posteriores a esa creación
	 * 
	 */
	public final void createContents() {
		if (!this.contentsReady) {
			this.createContents(this.createMainPanel());
			this.contentsReady = true;
		}
	}

	/**
	 * Este es el lugar en el que las subclases pueden crear los componentes hijos de esta ventana. Por
	 * defecto no hace nada y esto permite agregar componentes sin utilizar la herencia.
	 * 
	 * @param mainPanel El panel principal de esta ventana.
	 */
	public abstract void createContents(Panel mainPanel);

	/**
	 * Creación del panel principal de la ventana, todos los demás componentes dependerán de este.
	 * 
	 * @return El panel principal de la ventana.
	 */
	protected Panel createMainPanel() {
		return new Panel(this, this.getModel());
	}

	// ********************************************************
	// ** Acciones de la ventana
	// ********************************************************

	public void open() {
		this.createContents();

		// TODO Reemplazar esto por un log, o pensar un diseño.
		System.out.println(this.toString());

		WindowBuilder delegate = this.getDelegate();
		delegate.setTitle(this.title);
		delegate.setContents(this);
		delegate.setIcon(this.iconImage);
		delegate.setMinHeight(this.minHeight);
		delegate.open();
	}

	public void close() {
		this.delegate.close();
	}

	// ********************************************************
	// ** Internal
	// ********************************************************

	/**
	 * Agrega un {@link Widget} de este contenedor.
	 * 
	 * ATENCION: Los {@link Widget} se agregan a su {@link Container} por sí mismos en el momento de su
	 * creación. No se debe invocar este método manualmente.
	 * 
	 * @param child El {@link Widget} agregar.
	 */
	@Override
	public void addChild(Widget child) {
		this.children.add(child);
	}

	/**
	 * El delegate permite interactuar con la ventana física.
	 * 
	 * En muchos casos los delegates se usan sólo para la construcción inicial de los componentes físicos,
	 * pero en el caso de la ventana se debe mantener una referencia a este para poder utilizarla en acciones
	 * como {@link #close()}, o cualquier otro tipo de comportamiento que se invoca desde el modelo lógico de
	 * la vista hacia los componentes físicos de la misma en forma posterior a la construcción.
	 */
	@Override
	public WindowBuilder getDelegate() {
		if (this.delegate == null) {
			this.delegate = getOwner().getDelegate().createWindow();
		}
		return this.delegate;
	}

	public WindowOwner getOwner() {
		return this.owner;
	}

	/**
	 * Método interno. Sólo debe ser invocado o redefinido por componentes del framework.
	 * 
	 * @param delegate El panel principal de la ventana.
	 */
	@Override
	public void showOn(PanelBuilder delegate) {
		for (Widget widget : this.children) {
			widget.showOn(delegate);
		}
	}

	// ********************************************************
	// ** Utilities
	// ********************************************************

	@Override
	public String toString() {
		return HierarchicalLogger.hierarchicalToString(this);
	}

	@Override
	public void appendYourselfTo(HierarchicalLogger visitor) {
		visitor.append(this.getClass());
		visitor.append("children", this.children);
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setMinHeight(int minPreferedSize) {
		this.minHeight = minPreferedSize;
	}

}