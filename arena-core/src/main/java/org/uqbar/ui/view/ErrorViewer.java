package org.uqbar.ui.view;

import org.uqbar.arena.widgets.Container;

public interface ErrorViewer extends Container {

	public void showInfo(String message);

	public void showWarning(String message);

	public void showError(String message);

}
