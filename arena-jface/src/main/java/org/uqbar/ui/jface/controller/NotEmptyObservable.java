package org.uqbar.ui.jface.controller;

import java.util.Collection;

import org.eclipse.core.databinding.observable.Realm;

public class NotEmptyObservable extends SimpleJavaBeanObservable {

	public NotEmptyObservable(Object object, String propertyName) {
		super(Realm.getDefault(), object, getPropertyDescriptor(object.getClass(), propertyName));
	}

	@Override
	public Object doGetValue() {
		return !((Collection<?>) super.doGetValue()).isEmpty();
	}

	@Override
	public Object getValueType() {
		return Boolean.TYPE;
	}
}
