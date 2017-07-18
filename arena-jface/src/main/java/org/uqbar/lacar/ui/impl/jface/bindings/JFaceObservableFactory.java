package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.masterdetail.MasterDetailObservables;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.beans.BeanObservableMapDecorator;
import org.eclipse.core.internal.databinding.beans.BeanObservableSetDecorator;
import org.eclipse.core.internal.databinding.beans.BeanObservableValueDecorator;
import org.eclipse.core.internal.databinding.beans.JavaBeanPropertyObservableMap;
import org.eclipse.core.internal.databinding.observable.masterdetail.DetailObservableMap;
import org.uqbar.commons.model.utils.ScalaBeanInfo;
import org.uqbar.lacar.ui.impl.jface.bindings.observables.ArenaBeansObservables;

/**
 * 
 * @author npasserini
 */
public class JFaceObservableFactory {

	public static IObservableValue observeProperty(Object bean, String propertyChain) {
		return observeProperty(bean, getChainParts(propertyChain));
	}

	private static IObservableValue observeProperty(Object bean, List<String> propertyChainParts) {
		String firstProperty = propertyChainParts.get(0);

		DetailTransacionalObservableValue observableValue;
		Class<?> propertyType;
		IObservableFactory valueFactory;

		final Realm realm = Realm.getDefault();
		IObservableValue detailObservable = new JavaBeanTransacionalObservableValue(realm, bean, getPropertyDescriptor(
				bean.getClass(), firstProperty));

		for (int i = 1; i < propertyChainParts.size(); i++) {
			valueFactory = BeansObservables.valueFactory(realm, propertyChainParts.get(i));
			propertyType = getPropertyDescriptor((Class<?>) detailObservable.getValueType(), propertyChainParts.get(i))
				.getPropertyType();
			observableValue = new DetailTransacionalObservableValue(detailObservable, valueFactory, propertyType);
			detailObservable = new BeanObservableValueDecorator(observableValue, detailObservable,
				getPropertyDescriptor((Class<?>) detailObservable.getValueType(), propertyChainParts.get(i)));
		}
		return detailObservable;
	}

	public static IObservableSet observeSet(Object bean, String propertyChain) {
		List<String> propertyChainParts = getChainParts(propertyChain);

		if (propertyChainParts.size() > 1) {
			IObservableValue master = observeProperty(bean, propertyChainParts.subList(0, propertyChainParts.size() - 1));
			return observeDetailSet(Realm.getDefault(), master, 	propertyChainParts.get(propertyChainParts.size() - 1), null);
		}
		else {
			return observeSet(Realm.getDefault(), bean, propertyChain, null);
		}
	}
	
	public static IObservableSet observeSet(Realm realm, Object bean,	String propertyName, Class<?> elementType) {
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean.getClass(), propertyName);
		elementType = getCollectionElementType(elementType, propertyDescriptor);
		return new JavaBeanTransacionalObservableSet(realm, bean, propertyDescriptor, elementType);
	}
	
	
	/**
	 * Helper method for
	 * <code>MasterDetailObservables.detailSet(master, setFactory(realm,
	 propertyName), propertyType)</code>
	 * 
	 * @param propertyType
	 *            can be <code>null</code>
	 * @return an observable set that tracks the named property for the current
	 *         value of the master observable value
	 * 
	 * @see MasterDetailObservables
	 */
	public static IObservableSet observeDetailSet(Realm realm,
			IObservableValue master, String propertyName, Class<?> propertyType) {

		IObservableSet observableSet = detailSet(
				master, setFactory(realm, propertyName, propertyType),
				propertyType);
		BeanObservableSetDecorator decorator = new BeanObservableSetDecorator(
				observableSet, master, getValueTypePropertyDescriptor(master,
						propertyName));

		return decorator;
	}
	
	public static IObservableSet detailSet(IObservableValue master, IObservableFactory detailFactory, Object detailElementType) {
		return new DetailTransactionalObservableSet(detailFactory, master, detailElementType);
	}

	public static IObservableMap[] observeMaps(IObservableSet domain, Class<?> beanClass, String[] propertyNames) {
		 IObservableMap[] result = new IObservableMap[propertyNames.length];
		 int i = 0;
		 for (String propertyChain : propertyNames) {
			 result[i] = observeMap(domain, beanClass, propertyChain);
			 i++;
		}
		 
		 return result;
	 }

	public static JavaBeanTransacionalObservableMap observeMap(IObservableSet domain, Class<?> beanClass, String propertyChain) {
		List<String> propertyChainParts = getChainParts(propertyChain);
		return  new JavaBeanTransacionalObservableMap(domain, getPropertyDescriptor(beanClass, propertyChainParts.get(0)), propertyChainParts); 
	}
	
	public static JavaBeanTransacionalObservableMap observeDetailMap(IObservableSet domain, String propertyChain) {
		DetailTransactionalObservableSet observableSet = (DetailTransactionalObservableSet) ((BeanObservableSetDecorator) domain).getDelegate();
		return (JavaBeanTransacionalObservableMap) observeDetailMap(observeProperty(observableSet.getCurrentValue(), ((BeanObservableSetDecorator) domain).getPropertyDescriptor().getName()), propertyChain);
	}
	
	public static IObservableMap observeDetailMap(final IObservableValue master, final String propertyName) {
		DetailObservableMap detailObservableMap = new DetailObservableMap(new IObservableFactory() {
			public IObservable createObservable(Object target) {
				return new JavaBeanPropertyObservableMap(Realm.getDefault(), target, getPropertyDescriptor(target.getClass(), propertyName));
			}}, master);
		
		return new BeanObservableMapDecorator(detailObservableMap, master, getValueTypePropertyDescriptor(master,	propertyName));
	}

	public static IObservableList observeList(Object bean, String propertyChain) {
		List<String> propertyChainParts = getChainParts(propertyChain);

		if (propertyChainParts.size() > 1) {
			IObservableValue master = observeProperty(bean,	propertyChainParts.subList(0, propertyChainParts.size() - 1));
			return ArenaBeansObservables.observeDetailList(Realm.getDefault(), master, propertyChainParts.get(propertyChainParts.size() - 1), null);
		}
		else {
			// fix for scala collections
			return ArenaBeansObservables.observeList(Realm.getDefault(), bean, propertyChain, null);
		}
	}

	public static IObservableMap observeDetailMap(JavaBeanTransacionalObservableValue master, String propertyName) {
		return new JavaBeanPropertyObservableMap(Realm.getDefault(), master , getPropertyDescriptor((Class<?>) master.getObserved(), propertyName));
	}

	public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
		BeanInfo beanInfo;
		try {
			beanInfo = new ScalaBeanInfo(beanClass);
		}
		catch (IntrospectionException e) {
			return null;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			if (descriptor.getName().equals(propertyName)) {
				return descriptor;
			}
		}
		// Si no encontramos la propiedad quizás ésta existe 
		// pero las mayúsculas y minúsculas no están bien
		// Enviamos un mensaje de error más representativo
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			if (descriptor.getName().equalsIgnoreCase(propertyName)) {
				throw new BindingException("Property with name " + propertyName + " in class " + beanClass + " doesn't match expected getter/setter: " + descriptor.getName() + ". Please fix either the variable name or its accessors."); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		// Si no se encontró enviamos un mensaje de error
		throw new BindingException("Could not find property with name " + propertyName + " in class " + beanClass); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@SuppressWarnings("unchecked")
	protected static List<String> getChainParts(String propertyChain) {
		return Arrays.asList(propertyChain.split("\\."));
	}
	
	protected static Class<?> getCollectionElementType(Class<?> elementType, PropertyDescriptor propertyDescriptor) {
		if (elementType == null) {
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			return propertyType.isArray() ? propertyType.getComponentType() : Object.class;
		}
		return elementType;
	}
	
	/**
	 * @param elementType
	 *            can be <code>null</code>
	 * @return an observable set factory for creating observable sets
	 */
	public static IObservableFactory setFactory(final Realm realm,
			final String propertyName, final Class<?> elementType) {
		return new IObservableFactory() {
			public IObservable createObservable(Object target) {
				return observeSet(realm, target, propertyName, elementType);
			}
		};
	}
	
	/**
	 * @param observable the observable object owner of the property
	 * @param propertyName the name of the property
	 * @return property descriptor or <code>null</code>
	 */
	protected static PropertyDescriptor getValueTypePropertyDescriptor(
			IObservableValue observable, String propertyName) {
		return (observable.getValueType() != null) ? getPropertyDescriptor(
				(Class<?>) observable.getValueType(), propertyName) : null;
	}

}
