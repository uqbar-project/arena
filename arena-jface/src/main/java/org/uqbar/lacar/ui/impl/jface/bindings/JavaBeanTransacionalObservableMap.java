package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.core.databinding.observable.map.MapDiff;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.beans.BeanValueProperty;
import org.eclipse.core.internal.databinding.property.value.SetSimpleValueObservableMap;
import org.uqbar.arena.isolation.IsolationLevelEvents;
import org.uqbar.commons.utils.ReflectionUtils;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.apo.APOConfig;
import com.uqbar.common.transaction.ObjectTransaction;

/**
 * 
 * @author npasserini
 */
public class JavaBeanTransacionalObservableMap extends SetSimpleValueObservableMap {
	private final String isolationKey = "apo.poo.isolationLevel";
	private ObjectTransaction objectTransactionImpl = ObjectTransactionManager.getTransaction();
	private IsolationLevelEvents isolationLevelEvents = IsolationLevelEvents.valueOf(APOConfig.getProperty(isolationKey).value());
	private final List<String> propertyChain;

	
	public JavaBeanTransacionalObservableMap(IObservableSet domain, PropertyDescriptor propertyDescriptor, List<String> propertyChain) {
		  super(domain, new BeanValueProperty(propertyDescriptor, propertyDescriptor.getPropertyType()));
		this.propertyChain = propertyChain;
	}
	
	@Override
	protected void fireMapChange(MapDiff diff) {
		if (this.isolationLevelEvents.check(this.objectTransactionImpl)) {
			super.fireMapChange(diff);
		}
	}
	
	public Object getValue(Object model){
		return this.doGet(model);
	}
	
	protected Object doGet(Object key) {
		return super.doGet(changePropertyDespcriptor(key));
	}

	protected Object doPut(Object key, Object value) {
		return super.doPut(changePropertyDespcriptor(key), value);
	}
	
	protected Object changePropertyDespcriptor(Object model){
		try {
			Object currentModel = model;
			String currentProperty = propertyChain.get(0);
			for (int i = 0; i < propertyChain.size()-1; i++) {
				Method readMethod = JFaceObservableFactory.getPropertyDescriptor(currentModel.getClass(), propertyChain.get(i)).getReadMethod();
				if (!readMethod.isAccessible()) {
					readMethod.setAccessible(true);
				}
				currentModel = readMethod.invoke(currentModel, new Object[0]);
				currentProperty = propertyChain.get(i);
			}
			currentProperty = propertyChain.get(propertyChain.size()-1);
			
			PropertyDescriptor newPropertyDescriptor = JFaceObservableFactory.getPropertyDescriptor(currentModel.getClass(), currentProperty);
			hackPropertyDescriptor(newPropertyDescriptor);
			
			return currentModel;
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	protected void hackPropertyDescriptor(
			PropertyDescriptor newPropertyDescriptor)
			throws IllegalAccessException {
		Field propertyDescriptorField = ReflectionUtils.getField(BeanValueProperty.class, "propertyDescriptor");
		propertyDescriptorField.setAccessible(true);
		propertyDescriptorField.set(this.getProperty(), newPropertyDescriptor);
	}
	
}
