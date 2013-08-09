package org.uqbar.ui.jface;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * Simple base class for creating selection listeners, provides a default implementation of
 * {@link #widgetDefaultSelected(SelectionEvent)}
 * 
 * @author npasserini
 */
public abstract class AbstractSelectionListener implements SelectionListener {
	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		this.widgetSelected(event);
	}
}
