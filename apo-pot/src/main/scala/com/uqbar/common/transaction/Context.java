package com.uqbar.common.transaction;

import java.util.Map;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.uqbar.commons.collections.CollectionFactory;

/**
 * Encapsula el contexto correspondiente a un proceso que debe ejecutarse. Este contexto no se pasa por
 * parámetro, en cambio, se obtiene a partir de getCurrentContext de esta clase.
 * 
 * Para la mayoría de los usos, el contexto se considera válido una vez que se le ha asignado un owner. Un
 * contexto sin owner podría considerarse un NullObject, que no debe ser utilizado.
 * 
 * @author npasserini
 */
public class Context implements ContextConstants {
	private static final Log LOG = LogFactory.getLog(Context.class);

	private static ThreadLocal<Context> threadLocalContext = new ThreadLocal<Context>();

	private Map<String, Object> parameters;

	private static final Object DUMMY_OWNER = new Object() {
		public String toString() {
			return "DummyContextOwner";
		}
	};

	/*
	 * Static methods. Context creation, invalidation & retrieval.
	 */

	/**
	 * Inicializa en Contexto y le asocia un dueño, el cual podra terminar el Contexto; Si el Contexto ya fue
	 * inicializado solo lo usa; Si el Contexto fue creado con dueño Dummy se le asocia al primero que lo
	 * tome.
	 */
	public static Context beginContext(Object owner) {
		AssertUtils.assertArgumentNotNull("Context OWNER cannot be NULL!", owner);

		// TODO: synchronize
		Context current = threadLocalContext.get();
		if (current == null) {
			LOG.debug("Begining Context with owner: " + owner);
			current = new Context(owner);
			threadLocalContext.set(current);
		}
		else {
			if (current.isContextOwner(DUMMY_OWNER) && DUMMY_OWNER != owner) {
				current.setContextOwner(owner);
				LOG.debug("Asigning context to owner: " + owner);
			}
		}
		return current;
	}

	/**
	 * Termina el Contexto y libera todos los recurso asociados.
	 */
	public static void endContext(Object owner) {
		Context current = threadLocalContext.get();
		AssertUtils.assertNotNull("No initialized Context found to end!", current);

		if (!current.isContextOwner(DUMMY_OWNER)) {
			AssertUtils.assertTrue("Cannot end Context, the given object '" + owner + "' isn't the expected owner: "
				+ current.getContextOwner(), current.isContextOwner(owner));
		}
		LOG.debug("Ending Context with owner: " + owner);
		current.parameters.clear();
		threadLocalContext.set(null);
	}

	/**
	 * Obtiene el Contexto actual y si no existe uno crea uno.
	 */
	public static Context getCurrentContext() {
		Context current = threadLocalContext.get();
		if (current == null) {
			current = beginContext(DUMMY_OWNER);
		}
		return current;
	}


	/**
	 * Invalida el contexto actual, si existe un contexto padre se asigna ese.
	 */
	public static void unsecureInvalidateCurrentContext() {
		Context context = getCurrentContext();
		threadLocalContext.set(context.getParentContext());
	}

	/*
	 * Parameter management
	 */

	/**
	 * Devuelve el parámetro 'parameterName'
	 */
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String parameterName) {
		return (T) this.parameters.get(parameterName);
	}

	/**
	 * Agrega un nuevo parámetro a este contexto
	 */
	public void setParameter(String parameterName, Object parameterValue) {
		this.parameters.put(parameterName, parameterValue);
	}

	/**
	 * Elimina un parámetro de este contexto
	 */
	public void removeParameter(String parameterName) {
		this.parameters.remove(parameterName);
	}

	/**
	 * Determina si existe el parámetro 'parameterName' en este contexto.
	 */
	public boolean containsParameter(String parameterName) {
		return this.parameters.containsKey(parameterName);
	}


	/*
	 * Parent management
	 */

	/**
	 * Devuelve el owner del contexto.
	 */
	public Object getContextOwner() {
		return this.getParameter(CONTEXT_OWNER);
	}

	/**
	 * Permite al contexto modificar su owner. S�lo deber�a ser utilizado dentro del framework y para tareas
	 * espec�ficas de manejo de contextos.
	 */
	protected void setContextOwner(Object owner) {
		this.setParameter(CONTEXT_OWNER, owner);
	}

	/**
	 * Determina si el objeto es el dueño del contexto y por lo tanto es responsable de las tareas de
	 * administración.
	 */
	public boolean isContextOwner(Object obj) {
		return this.getParameter(CONTEXT_OWNER) == obj;
	}

	/**
	 * Determina si este contexto tiene un contexto padre.
	 */
	public boolean isChildContext() {
		return this.containsParameter(CONTEXT_PARENT);
	}

	/**
	 * Devuelve el contexto padre de este contexto.
	 */
	public Context getParentContext() {
		return (Context) this.getParameter(CONTEXT_PARENT);
	}

	/*
	 * Constructors (always protected)
	 */

	/**
	 * Constructor protegido.
	 */
	protected Context(Object owner) {
		this.parameters = CollectionFactory.createMap();
		this.setParameter(CONTEXT_OWNER, owner);
	}

	/**
	 * Constructor protegido.
	 */
	protected Context(Object owner, Context parentContext) {
		this(owner);
		this.setParameter(CONTEXT_PARENT, parentContext);
	}

	public Log getLog() {
		// LOG Manejar por contexto.
		return Context.LOG;
	}

	/*
	 * Stack available parameters
	 */

	public void pushParameter(String parameterName, Object parameterValue, Object owner) {
		if (this.containsParameter(parameterName)) {
			Stack<ParameterStackElement> stack = this.getParameterStack(parameterName);
			Object oldValue = this.getParameter(parameterName);
			Object oldOwner = this.getParameter(parameterName + ".owner");
			stack.push(new ParameterStackElement(oldValue, oldOwner));
		}
		this.setParameter(parameterName, parameterValue);
		this.setParameter(parameterName + ".owner", owner);
	}

	public void popParameter(String parameterName, Object owner) {
		if (getParameter(parameterName + ".owner") == owner) {
			Stack<ParameterStackElement> stack = this.getParameterStack(parameterName);
			if (stack.isEmpty()) {
				this.removeParameter(parameterName);
				this.removeParameter(parameterName + ".owner");
			}
			else {
				ParameterStackElement element = (ParameterStackElement) stack.pop();
				this.setParameter(parameterName, element.getValue());
				this.setParameter(parameterName + ".owner", element.getOwner());
			}

		}
	}

	private Stack<ParameterStackElement> getParameterStack(String parameterName) {
		if (this.containsParameter(parameterName + ".stack")) {
			return this.getParameter(parameterName + ".stack");
		}
		else {
			Stack<ParameterStackElement> stack = new Stack<ParameterStackElement>();
			this.setParameter(parameterName + ".stack", stack);
			return stack;
		}
	}

	/**
	 * 
	 * @author npasserini
	 */
	private class ParameterStackElement {
		private Object value, owner;

		private ParameterStackElement(Object value, Object owner) {
			this.value = value;
			this.owner = owner;
		}

		private Object getValue() {
			return this.value;
		}

		private Object getOwner() {
			return this.owner;
		}
	}
}