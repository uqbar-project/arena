package org.uqbar.arena.windows;

public class MessageBox {
	public static enum Type { Information, Warning, Error}
	
	private final Window<?> parent;
	private final Type type;
	private String message;

	public MessageBox(Window<?> parent, Type type) {
		this.parent = parent;
		this.type = type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void open() {
		this.parent.getDelegate().showMessage(this.type, this.message);
	}

}
