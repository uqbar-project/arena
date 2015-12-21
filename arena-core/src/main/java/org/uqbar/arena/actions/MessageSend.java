package org.uqbar.arena.actions;

import org.uqbar.commons.model.Repo;
import org.uqbar.commons.utils.ReflectionUtils;
import org.uqbar.lacar.ui.model.Action;

/**
 * 
 * Clase deprecada, debe usarse la interfaz {@link org.uqbar.lacar.ui.model.Action}.
 * 
 * Una acción implementada como un envío de mensaje a un objeto.
 * Se configura con el objeto al cual enviarle el mensaje y el nombre del metodo.
 * 
 * ATENCION: al hecho de que el metodo debe ser sin parametros.
 * 
 * Internamente utiliza reflection para invocar el metodo.
 * 
 * @author npasserini
 */
@Deprecated
public class MessageSend implements Action {
	private final Object target;
	private final String methodName;

	public MessageSend(Object target, String methodName) {
		this.target = target;
		this.methodName = methodName;
	}

	@Override
	public void execute() {
		ReflectionUtils.invokeMethod(this.target, this.methodName);
	}
	
	/**
	 * Returns a new {@link Action} object that is this same one
	 * but that will execute in a different thread asynchronously.
	 */
	public Action asAsync() {
		return new AsyncActionDecorator(this);
	}

}