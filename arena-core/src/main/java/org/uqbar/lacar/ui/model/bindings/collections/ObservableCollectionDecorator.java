package org.uqbar.lacar.ui.model.bindings.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
import org.apache.commons.collections15.collection.AbstractCollectionDecorator;

/**
 * Decora una coleccion haciéndola observable
 * 
 * @see ObservableContainer
 * 
 * @author jfernandes
 */
public class ObservableCollectionDecorator<E> extends AbstractCollectionDecorator<E> implements ObservableContainer {
	private Set<ChangeListener> listeners = new TreeSet<>();

	public ObservableCollectionDecorator(Collection<E> decoratee) {
		super(decoratee);
	}

	@Override
	public boolean add(E object) {
		boolean result = this.collection.add(object);
		if (result) {
			fireChange();
		}
		return result;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> coll) {
		boolean result = this.collection.addAll(coll);
		if (result) {
			fireChange();
		}
		return result;
	}
	
	@Override
	public boolean remove(Object object) {
		boolean result = this.collection.remove(object);
		if (result) {
			fireChange();
		}
		return result;
	}
	
	@Override
	public boolean removeAll(Collection<?> coll) {
		boolean result = this.collection.removeAll(coll);
		if (result) {
			fireChange();
		}
		return result;
	}
	
	@Override
	public void clear() {
		super.clear();
		fireChange();
	}
	
	@Override
	public boolean retainAll(Collection<?> coll) {
		boolean result = this.collection.retainAll(coll);
		if (result) {
			fireChange();
		}
		return result;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new AbstractIteratorDecorator(this.collection.iterator()) {
			@Override
			public void remove() {
				super.remove();
				fireChange();
			}
		};
	}

	protected void fireChange() {
		for (ChangeListener listener : listeners) {
			listener.handleChange();
		}
	}
	
	// ObservableCollection

	@Override
	public void addChangeListener(ChangeListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeChangeListener(ChangeListener listener) {
		this.listeners.remove(listener);
	}
	
}
