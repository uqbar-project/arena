package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;

public class FileSelector extends Button {

	private String title ="";
	private String path="";
	private String[] extensions;
	
	public FileSelector(Container container) {
		super(container);
	}
	
	public FileSelector title(String aTitle){
		this.title = aTitle;
		return this;
	}
	
	public FileSelector path(String aPath){
		this.path = aPath;
		return this;
	}
	
	public FileSelector extensions(String... extensions){
		this.extensions = extensions;
		return this;
	}
	
	@Override
	protected ButtonBuilder createBuilder(PanelBuilder container) {
		final ButtonBuilder button = container.addFileButton(this.getCaption(), this.title, this.path, this.extensions);
		this.configureSkineableBuilder(button);
		return button;
	}

}
