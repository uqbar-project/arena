package org.uqbar.arena.utils;

import org.uqbar.commons.model.annotations.Observable;
import org.uqbar.commons.model.annotations.TransactionalAndObservable;

public class ArenaUtils {

	public static Class<Observable> ObservableAnnotation = Observable.class;
	public static Class<TransactionalAndObservable> TransactionalAndObservableAnnotation = TransactionalAndObservable.class;

	public static boolean isObservable(Class<?> clazz) {
		if (observableClass(clazz)) return true;
		Class<?> parent = clazz.getSuperclass();
		if (parent == null) return false;
		return isObservable(parent);
	}

	private static boolean observableClass(Class<?> clazz) {
		return (clazz.isAnnotationPresent(ObservableAnnotation) || clazz.isAnnotationPresent(TransactionalAndObservableAnnotation));
	}
	
	public static String getRequiredAnnotationForModels() {
		return "[" + ObservableAnnotation.getSimpleName() + " , " + TransactionalAndObservableAnnotation.getSimpleName() + "]";
	}
}
