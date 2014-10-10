package org.uqbar.lacar.ui.impl.jface.swt.observables;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractVetoableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;

public class FileSelectorObservableValue extends AbstractVetoableValue {
	public boolean open = false;
	private final FileDialog fileDialog;
	
	public FileSelectorObservableValue(Control widget, String title, String path, String[] extensions) {
//		super(widget);
		this.fileDialog = new FileDialog(widget.getShell(), SWT.SAVE);
		this.fileDialog.setText(title);
		this.fileDialog.setFilterPath(path);
		this.fileDialog.setFilterExtensions(extensions);
	}
	
	public void openFile(){
		open = true;
		fireValueChange(Diffs.createValueDiff(null, ""));
	}

	@Override
	public Object getValueType() {
		return String.class;
	}

	@Override
	protected Object doGetValue() {
		if(open){
			open = false;
			return fileDialog.open();
		}else{
			return null;
		}
	}

	@Override
	protected void doSetApprovedValue(Object value) {
	}
}