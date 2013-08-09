package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.uqbar.lacar.ui.impl.jface.lists.JFaceAbstractListBuilder;

/**
 * @param<T> El tipo de objetos que contendrá el combo.
 */
public class JFaceSelectorBuilder<T> extends JFaceAbstractListBuilder<T, ComboViewer, Combo> {

	public JFaceSelectorBuilder(JFaceContainer container) {
		super(container);
	}

	protected ComboViewer createViewer(Composite jFaceComposite) {
		ComboViewer viewer = new ComboViewer(jFaceComposite, SWT.LEFT | SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);

		this.initialize(viewer.getCombo());
		return viewer;
	}
}
