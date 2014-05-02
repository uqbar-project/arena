package org.uqbar.lacar.ui.model.bindings;

import org.uqbar.arena.bindings.Adapter;
import org.uqbar.arena.bindings.Transformer;
import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.lacar.ui.model.adapter.NotEmptyTransformer;
import org.uqbar.lacar.ui.model.adapter.NotNullTransformer;

/**
 * Abstracción que vincula dos propiedades observables: una del modelo y otra de la vista. El binding se
 * completa con un {@link Transformer} que permite ajustar las diferencias entre los valores manejados por
 * modelo y vista.
 * 
 * @param<A> Adapter type
 * @author npasserini
 */
public class Binding<M, V extends Widget, C extends WidgetBuilder> {
	/**
	 * Referencia a una característica observable del modelo.
	 */
	private final Observable<M> model;

	/**
	 * Referencia a una característica observable de la vista.
	 */
	private ViewObservable<V, C> view;

	private Adapter adapter;

	/**
	 * Constructor con un adapter por default.
	 */
	public Binding(Observable<M> model, ViewObservable<V,C> view) {
		this.model = model;
		this.view = view;
	}
	
	public Binding<M,V,C> setView(ViewObservable<V,C> view){
		this.view = view;
		return this;
	}

	/**
	 * Asigna la estrategia que determina la forma de transformar los valores provenientes del modelo al
	 * formato requerido por la vista y viceversa.
	 * 
	 * This modifies the adapter of this binding. You should use only one of {@link #setAdapter(Adapter)} and
	 * {@link #setTransformer(Transformer)}.
	 * 
	 * @param adapter Un {@link Transformer}
	 * @return Este mismo {@link BindingBuilder}, para encadenar mensajes.
	 */
	public Binding<M,V,C> setTransformer(final Transformer<?, ?> transformer) {
		return this.setAdapter(new Adapter() {
			@Override
			public void configure(BindingBuilder binder) {
				binder.adaptWith(transformer);
			}
		});
	}
	
	public Binding<M,V,C> setModelToView(final com.uqbar.commons.collections.Transformer<M,?> transformer) {
		return this.setAdapter(new Adapter() {
			@Override
			public void configure(BindingBuilder binder) {
				binder.modelToView(transformer);
			}
		});
	}
	
	public Binding<M,V,C> setViewToModel(final com.uqbar.commons.collections.Transformer<?,M> transformer) {
		return this.setAdapter(new Adapter() {
			@Override
			public void configure(BindingBuilder binder) {
				binder.viewToModel(transformer);
			}
		});
	}

	/**
	 * This is the most general way to set the strategy that adapts the values in the model to the values in
	 * the view.
	 * 
	 * Only one adapter is supported, if this method is called twice, the first adapter will be discarded.
	 * 
	 * @param adapter
	 */
	public Binding<M,V,C> setAdapter(Adapter adapter) {
		this.adapter = adapter;
		return this;
	}

	public void execute(C control) {
		BindingBuilder binder = this.view.createBinding(control);
		this.model.configure(binder);

		if (this.adapter != null) {
			this.adapter.configure(binder);
		}

		binder.build();
	}

	/**
	 * Specifies that the output of this binding will be a boolean telling if the string input value is not
	 * null or empty.
	 */
	public Binding<M,V,C> notEmpty() {
		return this.setTransformer(new NotEmptyTransformer());
	}

	public Binding<M,V,C> notNull() {
		return this.setTransformer(new NotNullTransformer());
	}

}
