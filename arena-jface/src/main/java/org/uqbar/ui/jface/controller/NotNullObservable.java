package org.uqbar.ui.jface.controller;

import org.eclipse.core.databinding.observable.Realm;

public class NotNullObservable extends SimpleJavaBeanObservable {
	public NotNullObservable(Object object, String propertyName) {
		super(Realm.getDefault(), object, getPropertyDescriptor(object.getClass(), propertyName));
	}

	@Override
	public Object doGetValue() {
		return super.doGetValue() != null;
	}

	@Override
	public Object getValueType() {
		return Boolean.TYPE;
	}

}
