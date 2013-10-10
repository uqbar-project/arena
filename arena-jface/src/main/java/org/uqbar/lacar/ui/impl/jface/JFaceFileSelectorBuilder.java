package org.uqbar.lacar.ui.impl.jface;

import org.uqbar.lacar.ui.impl.jface.bindings.FileSelectorObservableValue;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.BindingBuilder;

public class JFaceFileSelectorBuilder extends JFaceButtonBuilder {
	private FileSelectorObservableValue value;
	private final String title;
	private final String[] extensions;
	private final String path;

	public JFaceFileSelectorBuilder(JFaceContainer context, String caption, String title, String path, String[] extensions) {
		super(context);
		this.title = title;
		this.path = path;
		this.extensions = extensions;
		this.setCaption(caption);
		this.onClick(this.openFileAction());
	}
	
	@Override
	public BindingBuilder observeValue() {
		this.value = new FileSelectorObservableValue(getWidget(), title, path, extensions);
		return new JFaceBindingBuilder(this, value);
	}

	protected Action openFileAction() {
		return new Action() {
			@Override
			public void execute() {
				value.openFile();
			}
		};
	}

}
