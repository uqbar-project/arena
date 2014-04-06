package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.swt.widgets.Control;
import org.uqbar.arena.widgets.Button;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.impl.jface.swt.observables.FileSelectorObservableValue;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.impl.jface.builder.JFaceButtonBuilder;

public class JFaceFileSelectorBuilder extends JFaceButtonBuilder {
	private FileSelectorObservableValue value;

	public JFaceFileSelectorBuilder(JFaceContainer context, String caption, String title, String path, String[] extensions) {
		super(context);
		this.setCaption(caption);
		this.onClick(this.openFileAction());
		//TODO: este casteo a Control no deberia ser necesario.
		// se esta mambeando con los generics ahora que la superclase esta en scala
		this.value = new FileSelectorObservableValue((Control) getWidget(), title, path, extensions);
	}
	
	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, this.value);
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
