package org.uqbar.arena.windows;

public class MessageBox {
	private final Window<?> parent;
	private final int style;
	private String message;

	public MessageBox(Window<?> parent, int style) {
		this.parent = parent;
		this.style = style;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void open() {
		this.parent.getDelegate().showMessage(this.style, this.message);
	}

}
