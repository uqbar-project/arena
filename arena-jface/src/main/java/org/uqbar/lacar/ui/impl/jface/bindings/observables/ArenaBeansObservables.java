package org.uqbar.lacar.ui.impl.jface.bindings.observables;

import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.masterdetail.MasterDetailObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.beans.BeanObservableListDecorator;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory;

/**
 * Es una hackeada, pero la unica forma.
 * Necesitamos meternos dentro de los observables de jface databinding
 * para que se lleven bien con scala, por ejemplo.
 * Para eso nos vemos obligados a hacer nuestras propias versiones.
 * 
 * @author jfernandes
 */
//TODO: creo que falta interceptar los observeSet()
public class ArenaBeansObservables {
	
	public static IObservableList observeList(Realm realm, Object bean, String propertyName, Class elementType) {
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean.getClass(), propertyName);
		elementType = getCollectionElementType(elementType, propertyDescriptor);
		return new ArenaJavaBeanObservableList(realm, bean, propertyDescriptor, elementType);
	}
	
	/*package*/ static PropertyDescriptor getPropertyDescriptor(Class beanClass, String propertyName) {
		// CHANGED from jface version to use our ScalaBeanInfo (to find scala's getters and setters)
		return JFaceObservableFactory.getPropertyDescriptor(beanClass, propertyName);
	}
	
	/*package*/ static Class getCollectionElementType(Class elementType,
			PropertyDescriptor propertyDescriptor) {
		if (elementType == null) {
			Class propertyType = propertyDescriptor.getPropertyType();
			elementType = propertyType.isArray() ? propertyType
					.getComponentType() : Object.class;
		}

		return elementType;
	}
	
	// copiado para pasarnos una anonima nuestra como listfactory, así después se en el binding de la property anidada
	// se crea un observable nuestro que entiende scala.
	public static IObservableList observeDetailList(Realm realm, IObservableValue master, String propertyName, Class propertyType) {
		IObservableList observableList = MasterDetailObservables.detailList(master, listFactory(realm, propertyName, propertyType), propertyType);
		BeanObservableListDecorator decorator = new BeanObservableListDecorator(observableList, master, getValueTypePropertyDescriptor(master, propertyName));
		return decorator;
	}
	
	public static IObservableFactory listFactory(final Realm realm, final String propertyName, final Class elementType) {
		return new IObservableFactory() {
			public IObservable createObservable(Object target) {
				return observeList(realm, target, propertyName, elementType);
			}
		};
	}
	
	/* package*/ static PropertyDescriptor getValueTypePropertyDescriptor(
			IObservableValue observable, String propertyName) {
		return (observable.getValueType() != null) ? getPropertyDescriptor(
				(Class) observable.getValueType(), propertyName) : null;
	}


}
