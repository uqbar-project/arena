 
package org.uqbar.lacar.ui.impl.jface.radiogroup;

import static org.uqbar.ui.swt.utils.SWTUtils.isCenterSet;
import static org.uqbar.ui.swt.utils.SWTUtils.isLeftSet;
import static org.uqbar.ui.swt.utils.SWTUtils.isRightSet;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TypedListener;

/**
 * A {@link Composite} control that represents a group of {@link RadioItem} items 
 * from which the user can only make a single-selection. 
 *
 * @author jfernandes
 */
public class RadioGroup extends Composite {
	private List<RadioItem> items = new ArrayList<RadioItem>();
	private RadioItem selectedItem = null;
	private int style;
	private int buttonStyle;

	public RadioGroup(Composite parent, int style) {
		super(parent, checkCompositeStyle(style));

		int directionStyle = (style & SWT.VERTICAL) != 0 ? SWT.VERTICAL : SWT.HORIZONTAL; 
		
		super.setLayout(new RowLayout(directionStyle));

		this.buttonStyle = checkButtonStyle(style);
		style = super.getStyle() | this.buttonStyle | directionStyle;

		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}

	public int getStyle() {
		return this.style;
	}

	/**
	 * Note: No Layout can be set on this Control because it already manages the
	 * size and position of its children.
	 */
	public void setLayout(Layout layout) {
		// cannot change layout !
		return;
	}

	private static int checkCompositeStyle(int style) {
		//REVIEWME:
		int result = style & SWT.BORDER;
		if ((style & SWT.LEFT_TO_RIGHT) != 0) {
			return result | SWT.LEFT_TO_RIGHT;
		}
		else if ((style & SWT.RIGHT_TO_LEFT) != 0) {
			return result | SWT.RIGHT_TO_LEFT;
		}
		return result;
	}

	private static int checkButtonStyle(int style) {
		int result = SWT.RADIO;
		//REVIEWME !
		if (isLeftSet(style)) {
			result |= SWT.LEFT;
		}
		else if (isCenterSet(style)) {
			result |= SWT.CENTER;
		}
		else if (isRightSet(style)) {
			result |= SWT.RIGHT;
		}			
		else {
			result |= SWT.LEFT;
		}
		return result;
	}

	protected void addItem(RadioItem item, int position) {
		if (position == -1) {
			position = this.getItemCount();
		}
		else if (position < 0 || position > getItemCount()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		this.items.add(position, item);
		this.redraw(item);
	}

	protected void redraw(RadioItem item) {
		this.layout(new Control[] { item.getButton() });		
	}

	void removeItem(RadioItem item) {
		this.checkWidget();
		this.items.remove(item);
		//shouldn't we call dispose ??
		if (this.getSelection() == item) {
			this.selectedItem = null;
		}
	}

	void itemSelected(RadioItem item, Event event) {
		if (item == this.selectedItem) {
			// the previously selected radio produces an event
			// so there are 2 events: 1) previous radio deselected, 2) new radio selected.
			// we only want to process the second one. 
			// Otherwise it will call the model twice: with null, and with the new selected value. 
			return;
		}
		this.selectedItem = item;
		this.notifyListeners(SWT.Selection, null);
	}

	public void remove(RadioItem item) {
		this.checkWidget();
		if (item.isDisposed() || item.getParent() != this) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		item.dispose();
	}

	public void remove(int position) {
		this.checkWidget();
		if (position < 0 || position >= this.getItemCount()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		this.items.get(position).dispose();
	}

	public synchronized void disposeItems() {
		this.checkWidget();
		this.setLayoutDeferred(true);
		try {
			for (RadioItem item : this.items.toArray(new RadioItem[this.items.size()])) {
				item.dispose();
			}
			this.items.clear();
		} finally {
			setLayoutDeferred(false);
		}
	}

	public void removeAll() {
		this.checkWidget();
		this.disposeItems();
	}

	/**
	 * Returns the number of items in the receiver.
	 * 
	 * @return the number of items in the receiver.
	 */
	public int getItemCount() {
		this.checkWidget();
		return this.items.size();
	}

	public void addSelectionListener(SelectionListener listener) {
		this.checkWidget();
		if (listener == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		TypedListener typedListener = new TypedListener(listener);
		this.addListener(SWT.Selection, typedListener);
		this.addListener(SWT.DefaultSelection, typedListener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		this.checkWidget();
		this.removeListener(SWT.Selection, listener);
		this.removeListener(SWT.DefaultSelection, listener);
	}

	public RadioItem getItem(int index) {
		this.checkWidget();
		return this.items.get(index);
	}
	
	public RadioItem[] getItems() {
		this.checkWidget();
		return this.items.toArray(new RadioItem[this.items.size()]);
	}

	public int indexOf(RadioItem item) {
		this.checkWidget();
		return this.items.indexOf(item);
	}

	public RadioItem getSelection() {
		this.checkWidget();
		return this.selectedItem;
	}

	public int getSelectionIndex() {
		this.checkWidget();
		return this.indexOf(this.selectedItem);
	}

	public void select(int index) {
		this.checkWidget();
		if (index < 0 || index >= getItemCount()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		this.changeSelectedTo(this.getItem(index));
	}

	public void deselectAll() {
		this.checkWidget();
		this.changeSelectedTo(null);
	}

	protected void changeSelectedTo(RadioItem newSelection) {
		if (this.selectedItem != null) {
			this.selectedItem.getButton().setSelection(false);
		}
		this.selectedItem = newSelection;
		if (newSelection != null) {
			this.selectedItem.getButton().setSelection(true);
		}
	}

	public int getButtonStyle() {
		return this.buttonStyle;
	}

}

