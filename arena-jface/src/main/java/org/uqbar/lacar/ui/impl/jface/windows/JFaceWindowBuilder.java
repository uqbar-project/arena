package org.uqbar.lacar.ui.impl.jface.windows;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.uqbar.arena.windows.MessageBox.Type;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.impl.jface.builder.JFacePanelBuilder;
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.ViewDescriptor;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.lacar.ui.model.WindowBuilder;
import org.uqbar.ui.view.ErrorViewer;

import com.uqbar.commons.collections.CollectionFactory;
import com.uqbar.commons.exceptions.ProgramException;

public class JFaceWindowBuilder extends AbstractWidgetBuilder implements WindowBuilder, JFaceContainer {
	private Window window;

	private DataBindingContext dbc;

	private List<WidgetBuilder> children = CollectionFactory.createList();

	private ViewDescriptor<PanelBuilder> windowDescriptor;

	// TODO Para no obligar a definir un ErrorViewer, podríamos tener uno
	// default que tira los errores por
	// consola o algo así.
	private ErrorViewer errorViewer;
	private String title;

	private AggregateValidationStatus status;

	private String iconImage;

	public JFaceWindowBuilder() {
		this.dbc = new DataBindingContext();
	}

	// ********************************************************
	// ** Configuración y apertura de la ventana
	// ********************************************************

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setContents(ViewDescriptor<PanelBuilder> windowDescriptor) {
		this.windowDescriptor = windowDescriptor;
	}
	
	@Override
	public void setIcon(String iconImage) {
		this.iconImage = iconImage;
		
	}

	/**
	 * Finaliza la construcción de la ventana utilizando un
	 * {@link ViewDescriptor} y la abre.
	 * 
	 * @param windowDescriptor
	 *            La descripción del contenido de la ventana.
	 */
	@Override
	public void open() {
		Window window = this.getJFaceWindow();

		// Esto crea tanto la ventana como sus contenidos (termina llamando a
		// createWindowContents).
		window.create();

		// Esto debe hacerse después del create, en caso contrario no hay shell
		// todavía.
		window.getShell().setText(this.title);
		window.getShell().pack();
		if(StringUtils.isNotEmpty(iconImage)){
			window.getShell().setImage(new Image(window.getShell().getDisplay(), iconImage));
		}

		// Una configuración adicional.
		window.setBlockOnOpen(true);

		window.getShell().addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				windowDescriptor.close();
			}
		});

		// Al hacer open se podría evitar el create anterior, pero necesito
		// hacerlo para poder hacer getShell
		// entre ambos.
		window.open();
	}

	@Override
	public void pack() {
		super.pack();
		for (WidgetBuilder child : this.children) {
			child.pack();
		}
	}

	protected Composite createWindowContents(Composite window) {
		JFacePanelBuilder builder = new JFacePanelBuilder(this);

		// TODO Está hardcodeado el layout del panel principal de la ventana.
		builder.setVerticalLayout();

		this.windowDescriptor.showOn(builder);

		this.pack();

		return builder.getWidget();

	}

	// ********************************************************
	// ** Ventanas y componentes hijos.
	// ********************************************************

	@Override
	public WindowBuilder createWindow() {
		return new JFaceDialogBuilder(this);
	}

	@Override
	public JFaceContainer addChild(WidgetBuilder child) {
		this.children.add(child);
		return this;
	}

	// ********************************************************
	// ** Acciones concretas, delegadas a la ventana física.
	// ********************************************************

	@Override
	public void showMessage(Type type, String message) {
		MessageBox messageBox = new MessageBox(this.getShell(), SWT.OK
				| computeStyle(type));
		messageBox.setMessage(message);
		messageBox.setText(this.getShell().getText());
		messageBox.open();
	}

	protected int computeStyle(Type type) {
		switch (type) {
		case Information:
			return SWT.ICON_INFORMATION;
		case Warning:
			return SWT.ICON_WARNING;
		case Error:
			return SWT.ICON_ERROR;
		default:
			throw new UnsupportedOperationException(
					"Invalid message box style: " + type);
		}
	}

	@Override
	public void close() {
		if (this.getShell() != null) {
			this.getShell().close();
		}
	}

	// ********************************************************
	// ** Manejo de errores
	// ********************************************************

	@Override
	public AggregateValidationStatus getStatus() {
		if (this.status == null) {
			this.status = new AggregateValidationStatus(
					this.getDataBindingContext(),
					AggregateValidationStatus.MAX_SEVERITY);
		}

		return this.status;
	}

	/**
	 * @throws ProgramException
	 *             Si no se le asignó un {@link ErrorViewer} a esta ventana.
	 *             Esto puede pasar si se intenta agregar un Panel de errores a
	 *             una ventana que no implementa la interfaz {@link ErrorViewer}
	 *             y tampoco se designó un {@link ErrorViewer} alternativo.
	 */
	@Override
	public ErrorViewer getErrorViewer() {
		if (this.errorViewer == null) {
			throw new ProgramException(
					"Esta ventana no tiene capacidad de mostrar errores por no habérsele configurado un ErrorViewer");
		}

		return this.errorViewer;
	}

	@Override
	public void setErrorViewer(ErrorViewer errorViewer) {
		this.errorViewer = errorViewer;
	}

	// ********************************************************
	// ** JFace Accessors
	// ********************************************************

	@Override
	public DataBindingContext getDataBindingContext() {
		return this.dbc;
	}

	@Override
	public Composite getJFaceComposite() {
		return this.getShell();
	}

	protected Shell getShell() {
		// WARNING: Este método falla si no se invocó el #create primero.
		return this.getJFaceWindow().getShell();
	}

	/**
	 * Este método crea la ventana subyacente si no existe.
	 * 
	 * @return
	 */
	public Window getJFaceWindow() {
		if (this.window == null) {
			this.window = this.createJFaceWindow();
		}
		return this.window;
	}

	protected Window createJFaceWindow() {
		return new ApplicationWindow(null) {
			@Override
			protected Control createContents(Composite window) {
				return JFaceWindowBuilder.this.createWindowContents(window);
			}
		};
	}

}
