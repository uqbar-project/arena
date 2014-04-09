 
package org.uqbar.lacar.ui.impl.jface.radiogroup;

import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.uqbar.ui.swt.utils.ArrayUtils;

/**
 * {@link ListViewer} implementation that uses a {@link RadioGroup} control
 * to represent options an selection.
 * 
 * @author jfernandes
 */
public class RadioGroupViewer extends AbstractListViewer {
	private RadioGroup group;

	public RadioGroupViewer(Composite parent) {
		this(parent, SWT.NONE);
	}

	public RadioGroupViewer(Composite parent, int style) {
		this(new RadioGroup(parent, style));
	}

	public RadioGroupViewer(RadioGroup group) {
		this.group = group;
		this.hookControl(group);
	}

	public RadioGroup getControl() {
		return this.group;
	}

	public void reveal(Object element) {
	}

	protected void listShowSelection() {
	}

	protected void listSetSelection(int[] indexes) {
		for (int i : indexes) {
			this.group.select(i);
		}
	}

	protected int[] listGetSelectionIndices() {
		return ArrayUtils.wrap(this.group.getSelectionIndex());
	}

	protected synchronized void listAdd(String string, int index) {
		this.group.setLayoutDeferred(true);
		try {
			new RadioItem(this.group, SWT.NONE, index).setText(string);
		} finally {
			this.group.setLayoutDeferred(false);
		}
	}

	protected void listDeselectAll() {
		this.group.deselectAll();
	}

	protected int listGetItemCount() {
		return this.group.getItemCount();
	}

	protected void listRemove(int index) {
		this.group.remove(index);
	}

	protected void listRemoveAll() {
		this.group.removeAll();
	}

	protected void listSetItem(int index, String string) {
		this.group.getItem(index).setText(string);
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listSetItems(String[] labels) {
		this.group.removeAll();
		for (String label : labels) {
			new RadioItem(this.group, SWT.NONE) //
				.setText(label);
		}
	}

}