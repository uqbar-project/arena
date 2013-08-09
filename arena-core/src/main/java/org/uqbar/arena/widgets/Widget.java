package org.uqbar.arena.widgets;

import java.util.Map;

import com.uqbar.commons.collections.CollectionFactory;
import com.uqbar.commons.loggeable.HierarchicalLogger;
import com.uqbar.commons.loggeable.Loggeable;

import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * Clase abstracta de la que heredan todos los demás componentes visuales.
 * 
 * @author npasserini
 */
public abstract class Widget implements Loggeable {
	/**
	 * Map of generated indices, allows to generate default captions for labels, buttons and other widgets.
	 * For each type of widget it contains the next index to be used.
	 */
	private static Map<Class<?>, Integer> indices = CollectionFactory.createMap();

	private final Container container;

	/**
	 * Simplest {@link Widget} creation.
	 * 
	 * @param container The {@link Panel} in with Widget will be added.
	 */
	public Widget(Container container) {
		this.container = container;
		container.addChild(this);
	}

	// ********************************************************
	// ** Inter widget-communication
	// ********************************************************

	protected Container getContainer() {
		return this.container;
	}

	/**
	 * Agrega este {@link Widget} al contenedor donde será dibujado. Una vez invocado a este método no se
	 * pueden hacer más modificaciones al {@link Widget}.
	 * 
	 * @param contenedor El {@link PanelBuilder} que interpretará la descripción de este Widget traduciéndolas
	 *            a las instrucciones necsarias para implementarlas en la tecnología subyacente.
	 */
	public abstract void showOn(PanelBuilder container);

	// ********************************************************
	// ** Utilities
	// ********************************************************

	/**
	 * Produces an automatic default autonumbered caption for any widget. The first generated caption is
	 * numbered "1".
	 */
	protected String nextCaption() {
		Class<?> cl = this.getClass();

		Integer index = Widget.indices.get(cl);
		if (index == null) {
			index = 1;
		}
		Widget.indices.put(cl, index + 1);

		return cl.getSimpleName() + index;
	}

	@Override
	public String toString() {
		return HierarchicalLogger.hierarchicalToString(this);
	}

	@Override
	public void appendYourselfTo(HierarchicalLogger visitor) {
		visitor.append(this.getClass());
	}
}
