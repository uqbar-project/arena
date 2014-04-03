package org.uqbar.arena.widgets;

import org.uqbar.arena.bindings.ObservableCaption;
import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.NoopAction;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

import com.uqbar.commons.collections.Closure;

public class Link extends SkinnableControl {

	protected String caption = this.nextCaption();
	protected Action onClick = new NoopAction();

	public Link(Container container) {
		super(container);
	}

	public <T extends Link>  T setCaption(String caption) {
		this.caption = caption;
		return  (T) this;
	}

	public <T extends Link> T onClick(Action onClick) {
		this.onClick = onClick;
		return (T) this;
	}

	protected String getCaption() {
		return this.caption;
	}

	// ********************************************************
	// ** Binding
	// ********************************************************
	
	public Link disableOnError() {
		this.addConfiguration(new Closure<ButtonBuilder>() {
			@Override
			public void execute(ButtonBuilder builder) {
				builder.disableOnError();
			}
		});
		return this;
	}

	public Binding<ButtonBuilder> bindCaptionToProperty(String propertyName) {
		return this.addBinding(new ObservableProperty(propertyName), new ObservableCaption<ButtonBuilder>());
	}
	
	
	// ********************************************************
	// ** Rendering
	// ********************************************************

	@Override
	protected ButtonBuilder createBuilder(PanelBuilder container) {
		final ButtonBuilder button = container.addLink(this.caption, this.onClick);
		this.configureSkineableBuilder(button);
		return button;
	}


}