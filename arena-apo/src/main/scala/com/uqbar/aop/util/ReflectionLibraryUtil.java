package com.uqbar.aop.util;

import java.util.Set;

import org.reflections.Reflections;

public class ReflectionLibraryUtil {
	
	public static Set<String> getSubTypesOf(Class<?> clazz){
		Reflections reflections = new Reflections(clazz.getPackage().getName());
	    return reflections.getStore().getSubTypesOf(clazz.getName());
	}
}
