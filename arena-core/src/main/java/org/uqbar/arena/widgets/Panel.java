package org.uqbar.arena.widgets;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.ArenaException;
import org.uqbar.arena.hierarchiallogger.HierarchicalLogger;
import org.uqbar.arena.layout.Layout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.commons.model.IModel;
import org.uqbar.commons.model.Model;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.ReflectionUtils;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * A generic container widget.
 * Commonly used with an specific layout object in order to layout
 * a set of related widgets.
 * 
 * It might have its own model, which eventually will be inherited to all
 * of its components.
 * 
 * @author npasserini
 */
public class Panel extends Widget implements Container {
	private static final long serialVersionUID = -7775790229491390428L;
	protected IModel<?> model;
	private int width = 250;

	/**
	 * Los componentes contenidos en este {@link Panel}
	 */
	private List<Widget> children = new ArrayList<Widget>();
	private Layout layout = new VerticalLayout();

	// ********************************************************
	// ** Panel creation
	// ********************************************************

	/**
	 * Creates a panel which by default inherits the model from its container.
	 */
	public Panel(Container container) {
		super(container);
	}

	public Panel(Container container, IModel<?> model) {
		super(container);
		this.model = model;
	}

	public Panel(Container container, Object model) {
		this(container, new Model(model));
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	/**
	 * Vincula el contenido de este panel con una propiedad del panel padre.
	 * 
	 * TODO WARNING: Este no es exactamente un "binding", se asigna el modelo correspondiente pero no quedan
	 * "vinculados", en caso de modificaciones posteriores al modelo el panel no se entera.
	 */
	public Panel bindContentsToProperty(String propertyName) {
		Object propertyModel = ReflectionUtils.invokeGetter(this.getModel().getSource(), propertyName);
		if (propertyModel instanceof IModel) {
			this.model = (IModel<?>) propertyModel;
		}
		else {
			this.model = new Model(propertyModel);
		}
		return this;
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	/**
	 * Creates the contents of the panel. Default behavior is to create no contents (i.e. an empty Panel).
	 * Subclasses can use this hook to configure specific contents.
	 */
	protected void createContents() {
	}

	public Panel setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}

	// ********************************************************
	// ** Container interface
	// ********************************************************

	@Override
	public void addChild(Widget child) {
		this.children.add(child);
	}

	/**
	 * Returns the model associated to this Panel.
	 * 
	 * If no model has been associated explicitly to the panel, the container's model is used.
	 */
	@Override
	public IModel<?> getModel() {
		this.validateModel();
		return this.model != null ? this.model : this.getContainer().getModel();
	}

	protected void validateModel() {
		if (this.model == null && this.getContainer().getModel() == null) {
			throw new UserException("El Panel no tiene un modelo asignado");
		}
	}

	public Object getModelObject() {
		return this.getModel().getSource();
	}

	// ********************************************************
	// ** Compilation into the real stuff (technology dependent components)
	// ********************************************************

	@Override
	public void showOn(PanelBuilder container) {
		this.createContents();
		if (this.layout == null) {
			throw new ArenaException("No se especificó un layout para el Panel");
		}

		PanelBuilder me = createPanel(container);

		this.layout.configure(me);

		for (Widget child : this.children) {
			child.showOn(me);
		}
	}
	
	protected List<Widget> getChildren() {
		return this.children;
	}

	/**
	 * template method.
	 */
	protected PanelBuilder createPanel(PanelBuilder container) {
		return container.addChildPanel();
	}

	// ********************************************************
	// ** Internal communication between Arena components
	// ********************************************************

	public Panel setWidth(int panelWidth) {
		this.width = panelWidth;
		return this;
	}

	public int getWidth() {
		return this.width;
	}

	// ********************************************************
	// ** Utilities
	// ********************************************************

	@Override
	public void appendYourselfTo(HierarchicalLogger visitor) {
		super.appendYourselfTo(visitor);
		visitor.append("model", this.getModel());
		visitor.append("layout", this.layout);
		visitor.append("children", this.children);
	}

}