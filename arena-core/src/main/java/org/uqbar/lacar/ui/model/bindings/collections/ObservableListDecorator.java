package org.uqbar.lacar.ui.model.bindings.collections;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections15.iterators.AbstractListIteratorDecorator;

/**
 * Decora una lista para convertirla en observable.
 * 
 * @author jfernandes
 */
public class ObservableListDecorator<E> extends ObservableCollectionDecorator<E> implements List<E> {

	public ObservableListDecorator(List<E> decoratee) {
		super(decoratee);
	}
	
	protected List<E> list() {
		return (List<E>) this.collection;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean result = list().addAll(index, c);
		if (result) {
			fireChange();
		}
		return result;
	}

	@Override
	public E get(int index) {
		return list().get(index);
	}

	@Override
	public E set(int index, E element) {
		E old = list().set(index, element);
		if (old != element) {
			fireChange();
		}
		return old;
	}

	@Override
	public void add(int index, E element) {
		list().add(index, element);
		fireChange();
	}

	@Override
	public E remove(int index) {
		E old = list().remove(index);
		fireChange();
		return old;
	}

	@Override
	public int indexOf(Object o) {
		return list().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list().lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return decorateIterator(list().listIterator());
	}

	protected ListIterator<E> decorateIterator(ListIterator<E> ite) {
		return new AbstractListIteratorDecorator<E>(ite) {
			@Override
			public void add(E obj) {
				super.add(obj);
				fireChange();
			}
			@Override
			public void remove() {
				super.remove();
				fireChange();
			}
			@Override
			public void set(E obj) {
				super.set(obj);
				fireChange();
			}
		};
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return decorateIterator(list().listIterator(index));
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO deberiamos tambien decorar eso ?
		return list().subList(fromIndex, toIndex);
	}
	

}
