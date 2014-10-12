package org.uqbar.arena.widgets;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.observables.ViewObservables;
import org.uqbar.arena.graphics.Image;
import org.uqbar.arena.widgets.traits.WidgetWithImage;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.LabelBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

import com.uqbar.commons.collections.Transformer;

/**
 * Representa una porción texto que se muestra dentro de un {@link Panel} y que no puede ser modificado por el
 * usuario.
 * 
 * @author npasserini
 */
public class Label extends SkinnableControl implements WidgetWithImage {
	private String text;

	public Label(Panel container) {
		super(container);
	}

	/**
	 * Configura el texto que debe mostrar este {@link Label}
	 */
	public Label setText(String text) {
		this.text = text;
		return this;
	}
	

	// ********************************************************
	// ** Inter-widget communication
	// ********************************************************

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		LabelBuilder labelBuilder = container.addLabel();
		
		this.configureSkineableBuilder(labelBuilder);
		
		if (this.text != null) {
			labelBuilder.setText(this.text);
		}
		
		return labelBuilder;
	}

	@Override
	public <M> Binding bindImageToProperty(String propertyName, Transformer<M,Image> transformer) {
		return this.addBinding(new ObservableProperty(propertyName), ViewObservables.observableImage(this, transformer));
	}

}
