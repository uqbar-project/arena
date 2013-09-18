package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;

public class JFaceListBuilder<T> extends JFaceAbstractListBuilder<T, ListViewer, List> {
	
	public JFaceListBuilder(JFaceContainer container) {
		super(container);
	}

	protected ListViewer createViewer(Composite jFaceComposite) {
		ListViewer viewer = new ListViewer(jFaceComposite, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		this.initialize(viewer.getList());
		return viewer;
	}
}
