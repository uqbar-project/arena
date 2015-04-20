package com.uqbar.poo.aop;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbar.commons.utils.ReflectionUtils;

/**
 * 
 * @author nnydjesus
 */
public class POOPropertyChangeSupport extends PropertyChangeSupport implements PropertySupport {
	private static final long serialVersionUID = 1L;
	private Object source;
	private Map<String, Map<String, List<String>>> mapping = new HashMap<>();

	public void addDependency(String clazz, String property, String dependency) {
		List<String> dependencies = dependenciesForClass(clazz, property);
		if (!dependencies.contains(dependency)) {
			dependencies.add(dependency);
		}
	}

	protected List<String> dependenciesForClass(String clazz, String property) {
		if (!mapping.containsKey(clazz)) {
			mapping.put(clazz, new HashMap<String, List<String>>());
		}
		if (!mapping.get(clazz).containsKey(property)) {
			mapping.get(clazz).put(property, new ArrayList<String>());
		}
		return mapping.get(clazz).get(property);
	}

	public List<String> dependenciesFor(String clazz, String property) {
		return dependenciesForClass(clazz, property);
	}

	public POOPropertyChangeSupport(Object sourceBean) {
		super(sourceBean);
		this.source = sourceBean;
	}

	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		super.firePropertyChange(this.convertProperty(propertyName), oldValue,
				newValue);
		List<String> dependencies = this.dependenciesFor(source.getClass().getName(), propertyName);
		for (String dependency : new ArrayList<>(dependencies)) {
			if (dependency != propertyName) {
				firePropertyChange(dependency, null,
						ReflectionUtils.invokeGetter(source, dependency));
			}
		}
	}

	public void addPropertyChangeListener(String propertyName,
			EventListener listener) {
		super.addPropertyChangeListener(propertyName,
				(PropertyChangeListener) listener);
	}

	public void removePropertyChangeListener(String propertyName,
			EventListener listener) {
		super.removePropertyChangeListener(propertyName,
				(PropertyChangeListener) listener);
	}

	protected String convertProperty(String propertyName) {
		if (propertyName.startsWith("_")) {
			return propertyName.substring(1, propertyName.length());
		} else {
			return propertyName;
		}
	}

}