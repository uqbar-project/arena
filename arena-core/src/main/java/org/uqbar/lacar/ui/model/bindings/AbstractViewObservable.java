package org.uqbar.lacar.ui.model.bindings;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.WidgetBuilder;

/**
 * base class for viewobservables
 * 
 * @author jfernandes
 */
public abstract class AbstractViewObservable<V extends Widget,C extends WidgetBuilder> implements ViewObservable<V, C> {
	private V view;
	
	public AbstractViewObservable(V view) {
		this.view = view;
	}

	@Override
	public V getView() {
		return this.view;
	}
	
	public <M> Binding<M, V, C> bindTo(Observable<M> observable) {
		return this.getView().addBinding(observable, this);
	}
	
	@Override
	public <M> Binding<M, V, C> bindToProperty(String propertyName) {
		return this.getView().addBinding(new ObservableProperty(propertyName), this);
	}

}
