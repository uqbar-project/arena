 
package org.uqbar.lacar.ui.impl.jface.radiogroup;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A concrete viewer based on a Nebula {@link RadioGroup} control.
 * <p>
 * <b>PROVISIONAL</b>: The superclass hierarchy of this viewer is provisional
 * and expected to change. Specifically we plan to change this class to extend a
 * new ItemViewer class in the near future. Therefore clients should avoid using
 * API declared only in AbstractListViewer (however APIs declared in
 * StructuredViewer and its superclasses are safe to use). Unsafe methods are
 * tagged "noreference" in the javadoc.
 * 
 * @since 3.5
 */
public class RadioGroupViewer extends AbstractListViewer {
	private RadioGroup group;

	/**
	 * Creates a radio group viewer on a newly-created {@link RadioGroup}
	 * control under the given parent. The viewer has no input, no content
	 * provider, a default label provider, no sorter, and no filters.
	 * 
	 * @param parent
	 *            the parent control
	 */
	public RadioGroupViewer(Composite parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Creates a radio group viewer on a newly-created {@link RadioGroup}
	 * control under the given parent. The radio group control is created using
	 * the given style bits. The viewer has no input, no content provider, a
	 * default label provider, no sorter, and no filters.
	 * 
	 * @param parent
	 *            the parent control
	 * @param style
	 *            SWT style bits
	 */
	public RadioGroupViewer(Composite parent, int style) {
		this(new RadioGroup(parent, style));
	}

	/**
	 * Creates a radio group viewer on the given {@link RadioGroup} control. The
	 * viewer has no input, no content provider, a default label provider, no
	 * sorter, and no filters.
	 * 
	 * @param composite
	 *            the composite control which is the immediate parent of all
	 *            radio buttons
	 */
	public RadioGroupViewer(RadioGroup group) {
		Assert.isNotNull(group);
		this.group = group;
		this.hookControl(group);
	}

	public Control getControl() {
		return this.group;
	}
	
	public RadioGroup getRadioGroup() {
		return this.group;
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	public void reveal(Object element) {
		// Do nothing -- element visibility is determined by layout
		// TODO - walk up parent hierarchy until we find a scrollable?
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listShowSelection() {
		// Do nothing -- selection visibility is determined by layout
		// TODO - walk up parent hierarchy until we find a scrollable?
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listSetSelection(int[] ixs) {
		for (int ix : ixs) {
			this.group.select(ix);
		}
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected int[] listGetSelectionIndices() {
		return new int[] { this.group.getSelectionIndex() };
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listAdd(String string, int index) {
		this.group.setLayoutDeferred(true);
		try {
			new RadioItem(this.group, SWT.NONE, index).setText(string);
		} finally {
			this.group.setLayoutDeferred(false);
		}
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listDeselectAll() {
		this.group.deselectAll();
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected int listGetItemCount() {
		return this.group.getItemCount();
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listRemove(int index) {
		this.group.remove(index);
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listRemoveAll() {
		this.group.removeAll();
	}

	/**
	 * @noreference Methods declared by AbstractListViewer should not be used by
	 *              clients. This class will be modified to extend ItemViewer at
	 *              some point in the future which will break references to
	 *              AbstractListViewer API.
	 */
	protected void listSetItem(int index, String string) {
		this.group.getItems()[index].setText(string);
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
			new RadioItem(this.group, SWT.NONE).setText(label);
		}
	}

}