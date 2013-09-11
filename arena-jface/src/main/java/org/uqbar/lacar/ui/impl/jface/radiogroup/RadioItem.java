 
package org.uqbar.lacar.ui.impl.jface.radiogroup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;

/**
 * An item from a {@link RadioGroup}.
 * 
 * @author jfernandes
 */
public class RadioItem extends Item {
	private RadioGroup parent;
	private Button button;

	public RadioItem(RadioGroup parent, int style) {
		this(parent, style, -1);
	}

	public RadioItem(final RadioGroup parent, int style, int index) {
		super(parent, style, checkIndex(parent, index));
		this.parent = parent;
		Listener disposeListener = this.createDisposeListener();
		parent.addListener(SWT.Dispose, disposeListener);

		Listener selectionListener = new Listener() {
			public void handleEvent(Event event) {
				parent.itemSelected(RadioItem.this, event);
			}
		};

		this.button = new Button(parent, parent.getButtonStyle());
		this.button.addListener(SWT.Selection, selectionListener);
		this.button.addListener(SWT.Dispose, disposeListener);
		
		this.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				if (parent != null) {
					parent.removeItem(RadioItem.this);
				}
				if (button != null) {
					button.dispose();
					if (parent != null && !parent.isDisposed())
						parent.layout(false);
				}
				RadioItem.this.parent = null;
				button = null;
			}
		});
		parent.addItem(this, index);
	}

	protected Listener createDisposeListener() {
		return new Listener() {
			public void handleEvent(Event event) {
				dispose();
			}
		};
	}

	private static int checkIndex(RadioGroup parent, int position) {
		if (position == -1) {
			return parent.getItemCount();
		}
		if (position < 0 || position > parent.getItemCount()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		return position;
	}

	public Button getButton() {
		return this.button;
	}

	public RadioGroup getParent() {
		return this.parent;
	}

	public String getText() {
		return this.safeGetButton().getText();
	}

	public void setText(String string) {
		this.safeGetButton().setText(string);
		this.parent.redraw(this);
	}

	public Image getImage() {
		return this.safeGetButton().getImage();
	}

	public void setImage(Image image) {
		this.safeGetButton().setImage(image);
		this.parent.layout(new Control[] { this.button });
	}

	public Color getForeground() {
		return this.safeGetButton().getForeground();
	}

	public void setForeground(Color foreground) {
		this.safeGetButton().setForeground(foreground);
	}

	public Color getBackground() {
		return this.safeGetButton().getBackground();
	}
	
	protected Button safeGetButton() {
		this.checkWidget();
		return this.button;
	}

	public void setBackground(Color background) {
		this.safeGetButton().setBackground(background);
	}

	public Font getFont() {
		return this.safeGetButton().getFont();
	}

	public void setFont(Font font) {
		this.safeGetButton().setFont(font);
	}

}