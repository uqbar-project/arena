package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Widget;
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder;

/**
 * Base class to every objects that build JFace {@link Widget}s 
 * 
 * @param <T> The type of widget that is built by this builder.
 * @author npasserini
 */
public abstract class JFaceWidgetBuilder<T extends Widget> extends AbstractWidgetBuilder {
	private T widget;
	private JFaceContainer container;

	/**
	 * Creates a builder that will configure the given widget.
	 * 
	 * @param container Another widget that contains this one.
	 * @param jFaceWidget The widget that this builder will configure.
	 */
	public JFaceWidgetBuilder(JFaceContainer container, T jFaceWidget) {
		this(container);
		this.initialize(jFaceWidget);
	}
	
	/**
	 * Creates a builder that will create a widget and configure it.
	 * 
	 * @param container Another widget that contains this one.
	 */
	public JFaceWidgetBuilder(JFaceContainer container) {
		this.container = container;
		this.container.addChild(this);
	}

	/**
	 * It is mandatory to call this method for subclasses that create the widget internally.
	 * 
	 * @param jFaceWidget
	 */
	protected void initialize(T jFaceWidget) {
		this.widget = jFaceWidget;
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************

	public T getWidget() {
		return this.widget;
	}

	public JFaceContainer getContainer() {
		return this.container;
	}

	public DataBindingContext getDataBindingContext() {
		return this.getContainer().getDataBindingContext();
	}

	protected String getDescription() {
		return this.getClass().getSimpleName();
	}
}
