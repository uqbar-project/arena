package org.uqbar.lacar.ui.model.bindings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.uqbar.lacar.ui.model.bindings.collections.ObservableCollectionDecorator;
import org.uqbar.lacar.ui.model.bindings.collections.ObservableListDecorator;

/**
 * Factory methods to create observable collections.
 * 
 * @author jfernandes
 */
public class Observables {
	
	public static <E> List<E> newList(E... elements) {
		return new ObservableListDecorator<E>(new ArrayList<E>(Arrays.asList(elements)));
	}
	
	public static <E> Collection<E> newCollection() {
		return new ObservableCollectionDecorator<E>(new ArrayList<E>());
	}

}
