package org.uqbar.lacar.ui.impl.jface.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.uqbar.commons.model.UserException;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.model.Action;

/**
 * Wrapps a {@link SelectionListener} and manages its exceptions.
 * 
 * @author npasserini
 */
public class JFaceActionAdapter implements SelectionListener {
	private final JFaceContainer context;

	// TODO No está bien referenciar al Arena desde Lacar.
	private final Action action;

	/**
	 * @param context
	 *            Where to show errors
	 * @param delegate
	 *            The real listener which will execute the actual task.
	 * @param actionName
	 *            The name of the action, could be used for detailed error
	 *            messages.
	 */
	public JFaceActionAdapter(JFaceContainer context, Action delegate) {
		this.action = delegate;
		this.context = context;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		this.widgetSelected(event);
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		try {
			this.action.execute();
		} 
		catch (UserException exception) {
			this.context.getErrorViewer().showError(exception.getMessage());
		} 
		catch (RuntimeException exception) {
			exception.printStackTrace();
			this.context.getErrorViewer()
					.showError("Se produjo un error de sistema. Puede revisar el log de la aplicación para obtener más detalles");
		}
	}
}
