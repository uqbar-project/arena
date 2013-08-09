package org.uqbar.ui.jface.base;

import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

/**
 * Enumeración de propiedades bindeables de los controles
 * 
 * @author fernando
 */
public enum UIProperty {
	BACKGROUND() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeBackground(control);
		}
	},
	EDITABLE() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeEditable(control);
		}
	},
	ENABLED() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeEnabled(control);
		}
	},
	FONT() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeFont(control);
		}
	},
	FOREGROUND() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeForeground(control);
		}
	},
	SELECTION() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeSelection(control);
		}
	},
	TEXT() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeText(control, SWT.FocusOut);
		}
	},
	TOOLTIPTEXT() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeTooltipText(control);
		}
	},
	VISIBLE() {
		@Override
		public ISWTObservableValue observeProperty(Control control) {
			return SWTObservables.observeVisible(control);
		}
	},
	;

	public ISWTObservableValue observeProperty(Control control) {
		return observeProperty(control);
	}

}
