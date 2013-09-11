package org.uqbar.arena.windows;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.ui.view.ErrorViewer;

/**
 * Estas ventanas, al implementar {@link ErrorViewer} se registran como listeners de los errores que se
 * produzcan dentro de la ventana y tienen la capacidad de mostrar automáticamente estos errores al usuario. |
 * 
 * Es decir que al heredar de esta ventana se hereda la esctructura
 * 
 * @param <T> El tipo del modelo de esta BuscarSociosWindowventana
 * 
 * @author npasserini
 */
public abstract class SimpleWindow<T> extends Window<T> implements ErrorViewer {
	private String taskDescription;

	public SimpleWindow(WindowOwner parent, T model) {
		super(parent, model);
		// Registrarse como listener de los errores para poder mostrarlos en pantalla.
		this.getDelegate().setErrorViewer(this);
	}

	// ********************************************************
	// ** Contents
	// ********************************************************

	@Override
	public void createContents(Panel mainPanel) {
		this.configureLayout(mainPanel);
		this.createMainTemplate(mainPanel);
	}

	// ********************************************************
	// ** Abstract methods
	// ********************************************************

	protected abstract void addActions(Panel actionsPanel);

	protected abstract void createFormPanel(Panel mainPanel);

	// ********************************************************
	// ** Window layout template
	// ********************************************************

	protected void createMainTemplate(Panel mainPanel) {
		this.createErrorsPanel(mainPanel);
		this.createFormPanel(mainPanel);
		this.createActionsPanel(mainPanel);
	}

	protected void configureLayout(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
	}

	protected ErrorsPanel createErrorsPanel(Panel mainPanel) {
		return new ErrorsPanel(mainPanel, this.getTaskDescription());
	}

	protected void createActionsPanel(Panel mainPanel) {
		Panel actionsPanel = new Panel(mainPanel);
		actionsPanel.setLayout(new HorizontalLayout());
		this.addActions(actionsPanel);
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************

	/**
	 * La descripción de la tarea, que se usa en el panel de errores cuando no hay errores. En caso de no
	 * asignarse una descripción se utiliza el título de la ventana.
	 */
	public String getTaskDescription() {
		return this.taskDescription != null ? this.taskDescription : this.getTitle();
	}

	/**
	 * Modifica la descripción de la tarea.
	 * 
	 * @param taskDescription Nuevo texto, para usar en el panel de errores si no hay errores.
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	// ********************************************************
	// ** Mensajes (ErrorViewer)
	// ********************************************************

	@Override
	public void showInfo(String message) {
		this.showMessageBox(MessageBox.Type.Information, message);
	}

	@Override
	public void showWarning(String message) {
		this.showMessageBox(MessageBox.Type.Warning, message);
	}

	@Override
	public void showError(String message) {
		this.showMessageBox(MessageBox.Type.Error, message);
	}

	protected void showMessageBox(MessageBox.Type type, String message) {
		MessageBox messageBox = new MessageBox(this, type);
		messageBox.setMessage(message);
		messageBox.open();
	}

}