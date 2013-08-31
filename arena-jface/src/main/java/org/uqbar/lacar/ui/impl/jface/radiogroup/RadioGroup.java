 
package org.uqbar.lacar.ui.impl.jface.radiogroup;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TypedListener;

/**
 * <p>
 * SWT Widget that presents a group of radio buttons. This widget require
 * jdk-1.4+
 * </p>
 * <p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>BORDER, HORIZONTAL, VERTICAL, LEFT, RIGHT, CENTER, LEFT_TO_RIGHT,
 * RIGHT_TO_LEFT</dd>
 * </dl>
 * <p>
 * Note: Only one of the styles HORIZONTAL and VERTICAL may be specified.
 * </p>
 * <p>
 * Note: Only one of the styles LEFT, RIGHT, and CENTER may be specified.
 * </p>
 * <p>
 * Note: Only one of the styles LEFT_TO_RIGHT and RIGHT_TO_LEFT may be
 * specified.
 * </p>
 * <p>
 * <dl>
 * <dt><b>Events:</b></dt>
 * <dd>Selection, DefaultSelection</dd>
 * </dl>
 * </p>
 * <p>
 * NOTE: THIS WIDGET AND ITS API ARE STILL UNDER DEVELOPMENT. THIS IS A
 * PRE-RELEASE ALPHA VERSION. USERS SHOULD EXPECT API CHANGES IN FUTURE
 * VERSIONS.
 * </P>
 * 
 * @author Matthew Hall <matthall@woodcraftmill.com>
 */
public class RadioGroup extends Composite {
	private RadioItem[] items = {};

	private RadioItem selectionItem = null;
	private int selectionIndex = -1;

	/**
	 * Scrolling direction flag. True : V_SCROLL, false : H_SCROLL
	 */
	private boolean vertical = true;

	private int style;
	private int buttonStyle;

	private RowLayout layout;

	public RadioGroup(Composite parent, int style) {
		super(parent, checkCompositeStyle(style));

		this.vertical = (style & SWT.VERTICAL) != 0;
		this.layout = new RowLayout(this.vertical ? SWT.VERTICAL : SWT.HORIZONTAL);
		super.setLayout(this.layout);

		this.buttonStyle = checkButtonStyle(style);
		style = super.getStyle() | this.buttonStyle | (this.vertical ? SWT.VERTICAL : SWT.HORIZONTAL);

		setBackgroundMode(SWT.INHERIT_DEFAULT);
	}

	public int getStyle() {
		return this.style;
	}

	/**
	 * Sets the layout which is associated with the receiver to be the argument
	 * which may be null.
	 * <p>
	 * Note: No Layout can be set on this Control because it already manages the
	 * size and position of its children.
	 * </p>
	 * 
	 * @param layout
	 *            the receiver's new layout or null
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setLayout(Layout layout) {
		this.checkWidget();
		return;
	}

	private static int checkCompositeStyle(int style) {
		// V_SCROLL == VERTICAL, H_SCROLL == HORIZONTAL
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
		if ((style & SWT.LEFT) != 0) {
			result |= SWT.LEFT;
		}
		else if ((style & SWT.CENTER) != 0) {
			result |= SWT.CENTER;
		}
		else if ((style & SWT.RIGHT) != 0) {
			result |= SWT.RIGHT;
		}			
		else {
			result |= SWT.LEFT;
		}
		return result;
	}

	void addItem(RadioItem item, int position) {
		if (position == -1) {
			position = getItemCount();
		}
		else if (position < 0 || position > getItemCount()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		RadioItem[] newItems = new RadioItem[items == null ? 1 	: items.length + 1];

		if (position == -1) {
			position = newItems.length - 1;
		}

		if (items == null) {
			items = new RadioItem[] { item };
		} else {
			if (items.length > position) {
				item.getButton().moveAbove(items[position].getButton());
			}

			System.arraycopy(items, 0, newItems, 0, position);
			newItems[position] = item;
			System.arraycopy(items, position, newItems, position + 1, items.length - position);
			items = newItems;
		}

		layout(new Control[] { item.getButton() });

		if (selectionIndex >= position) {
			selectionIndex++;
		}
	}

	void removeItem(RadioItem item) {
		checkWidget();
		int position = indexOf(item);
		if (position != -1) {
			RadioItem[] newItems = new RadioItem[items.length - 1];
			System.arraycopy(this.items, 0, newItems, 0, position);
			System.arraycopy(this.items, position + 1, newItems, position, newItems.length - position);
			this.items = newItems;
		}

		if (this.selectionIndex == position) {
			this.selectionItem = null;
			this.selectionIndex = 0;
		} else if (this.selectionIndex > position) {
			this.selectionIndex--;
		}
	}

	void itemSelected(RadioItem item, Event event) {
		if (item == this.selectionItem) {
			// the previously selected radio produces an event
			// so there are 2 events: 1) previous radio deselected, 2) new radio selected.
			// we only want to process the second one. 
			// Otherwise it will call the model twice: with null, and with the new selected value. 
			return;
		}
		this.selectionItem = item;
		this.selectionIndex = indexOf(item);
		this.notifyListeners(SWT.Selection, null);
	}

	public void clear(int i) {
		this.checkWidget();
		RadioItem item = this.items[i];
		item.setText("");
		item.setImage(null);
		item.setFont(this.getFont());
		item.setForeground(this.getForeground());
		item.setBackground(this.getBackground());
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
		if (position < 0 || position >= getItemCount()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		this.items[position].dispose();
	}

	public void remove(int start, int end) {
		this.checkWidget();
		if (start > end) {
			return;
		}
		if (start < 0 || end >= this.items.length) {
			SWT.error(SWT.ERROR_INVALID_RANGE);
		}

		this.setLayoutDeferred(true);
		try {
			Item[] items = (Item[]) this.items.clone();
			for (int i = start; i <= end; i++) {
				items[i].dispose();
			}
		} finally {
			setLayoutDeferred(false);
		}
	}

	public void removeAll() {
		this.checkWidget();
		this.remove(0, items.length - 1);
	}

	/**
	 * Returns the number of items in the receiver.
	 * 
	 * @return the number of items in the receiver.
	 */
	public int getItemCount() {
		this.checkWidget();
		if (this.items == null) {
			return 0;
		}
		return this.items.length;
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

	public RadioItem[] getItems() {
		this.checkWidget();
		return this.items == null ? new RadioItem[0] : Arrays.copyOf(this.items, this.items.length);
	}

	public int indexOf(RadioItem item) {
		this.checkWidget();
		if (this.items == null) {
			return -1;
		}
		for (int i = 0; i < this.items.length; i++) {
			if (this.items[i] == item)
				return i;
		}
		return -1;
	}

	public RadioItem getSelection() {
		this.checkWidget();
		return this.selectionItem;
	}

	public int getSelectionIndex() {
		this.checkWidget();
		return this.selectionIndex;
	}

	public void setSelection(RadioItem item) {
		this.checkWidget();
		if (item == null) {
			this.deselectAll();
			return;
		}
		if (item.getParent() != this) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		item.getButton().setSelection(true);
	}

	public void deselectAll() {
		this.checkWidget();
		if (this.selectionItem != null) {
			this.selectionItem.getButton().setSelection(false);
			this.selectionItem = null;
			this.selectionIndex = -1;
		}
	}

	public void select(int index) {
		this.checkWidget();
		if (index < 0 || index >= getItemCount()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		this.items[index].getButton().setSelection(true);
	}

	public void reveal(RadioItem item) {
		this.checkWidget();
		// TODO
	}
	
	public int getButtonStyle() {
		return this.buttonStyle;
	}
}

