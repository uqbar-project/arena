package org.uqbar.lacar.ui.impl.jface.radiogroup;

import org.eclipse.swt.widgets.Composite;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.impl.jface.lists.JFaceAbstractListBuilder;

/**
 * Jface builder for radiogroup selectors
 * 
 * @author jfernandes
 */
public class JFaceRadioGroupBuilder<T> extends JFaceAbstractListBuilder<T, RadioGroupViewer, RadioGroup> {

	public JFaceRadioGroupBuilder(JFaceContainer container) {
		super(container);
	}

	@Override
	protected RadioGroupViewer createViewer(Composite jFaceComposite) {
		RadioGroupViewer theViewer = new RadioGroupViewer(jFaceComposite);
		this.initialize(theViewer.getRadioGroup());
		return theViewer;
	}

}
