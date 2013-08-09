package org.uqbar.ui.jface.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public class Binder {
	private DataBindingContext dataBindingContext;
	
	public Binder(DataBindingContext context) {
		this.dataBindingContext = context;
	}
	
	public Binder() {
		this(new DataBindingContext());
	}
	
	public DataBindingContext getDataBindingContext() {
		return this.dataBindingContext;
	}
	
	// ********************************************************
	// ** Binding utilities
	// ********************************************************
	
	public IObservableValue observeNestedDetailValue(Object model, String propertyName) { 
		String[] parts = propertyName.split("."); 
		
		IObservableValue detailObservable = BeansObservables.observeValue(model, parts[0]);
		for (int i = 1; i < parts.length; i++) {
			detailObservable = BeansObservables.observeDetailValue(Realm.getDefault(), detailObservable, parts[i] , getPropertyDescriptor(detailObservable.getValue().getClass(), parts[i]).getPropertyType());
		}
		
		return detailObservable; 
	} 

	/**
	 * Vincula un control con una propiedad del modelo.
	 */
	public void bindText(Object model, String propertyName, Control control) {
		this.bind(SWTObservables.observeText(control, SWT.Modify), observeNestedDetailValue(model, propertyName));
	}

	public void bind(Object model, UIProperty observableProperty, String propertyName, Control control) {
		this.bind(observableProperty.observeProperty(control), observeNestedDetailValue(model, propertyName));
	}
	
	/**
	 * Vincula dos observables entre sí (uno visual de swt con uno del modelo).
	 */
	public void bind(ISWTObservableValue observableTarget, IObservableValue observableModel) {
		this.getDataBindingContext().bindValue(//
			observableTarget, //
			observableModel, //
			new BaseUpdateValueStrategy(), //
			new BaseUpdateValueStrategy());
	}
	
	public PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			// cannot introspect, give up
			return null;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			if (descriptor.getName().equals(propertyName)) {
				return descriptor;
			}
		}
		throw new BindingException(
				"Could not find property with name " + propertyName + " in class " + beanClass); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
