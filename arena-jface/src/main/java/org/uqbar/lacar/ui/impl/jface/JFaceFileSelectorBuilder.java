package org.uqbar.lacar.ui.impl.jface;

import org.uqbar.lacar.ui.impl.jface.bindings.FileSelectorObservableValue;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.BindingBuilder;

public class JFaceFileSelectorBuilder extends JFaceButtonBuilder {
	private FileSelectorObservableValue value;

	public JFaceFileSelectorBuilder(JFaceContainer context, String caption, String title, String path, String[] extensions) {
		super(context);
		this.setCaption(caption);
		this.onClick(this.openFileAction());
		this.value = new FileSelectorObservableValue(getWidget(), title, path, extensions);
	}
	
	@Override
	public BindingBuilder observeValue() {
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
