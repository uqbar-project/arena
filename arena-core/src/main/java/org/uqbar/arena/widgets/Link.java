package org.uqbar.arena.widgets;

import org.uqbar.arena.bindings.ObservableCaption;
import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.NoopAction;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.builder.LinkBuilder;

import com.uqbar.commons.collections.Closure;

public class Link extends SkinnableControl {
	protected String caption = this.nextCaption();
	protected Action onClick = new NoopAction();

	public Link(Container container) {
		super(container);
	}

	public Link setCaption(String caption) {
		this.caption = "<a>"+caption+"</a>";
		return this;
	}

	public Link onClick(Action onClick) {
		this.onClick = onClick;
		return this;
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

	public Binding<?,Link, ButtonBuilder> bindCaptionToProperty(String propertyName) {
		return this.addBinding(new ObservableProperty(propertyName), new ObservableCaption(this));
	}
	
	
	// ********************************************************
	// ** Rendering
	// ********************************************************

	@Override
	protected LinkBuilder createBuilder(PanelBuilder container) {
		final LinkBuilder button = container.addLink(this.caption, this.onClick);
		this.configureSkineableBuilder(button);
		return button;
	}


}