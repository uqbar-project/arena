package org.uqbar.ui.jface.builder;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ActionsBuilder {
	private Composite main;
	private IObservableValue enabledOn;
	private DataBindingContext dataBinding;

	public ActionsBuilder(Composite parent, int style) {
		this.main = new Composite(parent, SWT.NONE);
		this.main.setLayout(this.createLayout(style));
	}

	protected FillLayout createLayout(int style) {
		FillLayout layout = new FillLayout(style);
		layout.spacing = 3;
		return layout;
	}

	public Button addButton(String text, SelectionListener listener) {
		Button button = new Button(this.main, SWT.PUSH);
		button.setText(text);
		button.addSelectionListener(listener);

		if (this.enabledOn != null) {
			this.dataBinding.bindValue(SWTObservables.observeEnabled(button), this.enabledOn, null, null);
		}
		return button;
	}

	public void enabledOn(IObservableValue observableValue) {
		this.enabledOn = observableValue;
		this.dataBinding = new DataBindingContext();
	}
}
