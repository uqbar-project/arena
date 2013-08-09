package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.uqbar.arena.bindings.Transformer;
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.ui.jface.base.BaseUpdateValueStrategy;

public class JFaceBindingBuilder implements BindingBuilder {
	private final DataBindingContext dbc;
	private IObservableValue model;
	private IObservableValue view;
	private UpdateValueStrategy viewToModel = new BaseUpdateValueStrategy();
	private UpdateValueStrategy modelToView = new BaseUpdateValueStrategy();

	public JFaceBindingBuilder(DataBindingContext dbc, IObservableValue view, IObservableValue model) {
		this.dbc = dbc;
		this.view = view;
		this.model = model;
	}

	public JFaceBindingBuilder(JFaceWidgetBuilder<?> widget, IObservableValue view, IObservableValue model) {
		this(widget.getDataBindingContext(), view, model);
	}

	/**
	 * Creates a {@link JFaceObservableFactory} whose model will be assigned later (using
	 * {@link #observeProperty(Object, String)}
	 * 
	 * @param widget
	 * @param view
	 */
	public JFaceBindingBuilder(JFaceWidgetBuilder<?> widget, IObservableValue view) {
		this(widget, view, null);
	}

	@Override
	public void observeProperty(Object model, String propertyName) {
		this.model = JFaceObservableFactory.observeProperty(model, propertyName);
	}

	protected void setConverter(UpdateValueStrategy viewToModel, IConverter converter) {
		viewToModel.setConverter(converter);
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	@SuppressWarnings("unchecked")
	@Override
	public <M, V> BindingBuilder adaptWith(final Transformer<M, V> transformer) {
		this.setConverter(this.viewToModel, new IConverter() {
			@Override
			public Object getToType() {
				return transformer.getModelType();
			}

			@Override
			public Object getFromType() {
				return transformer.getViewType();
			}

			@Override
			public Object convert(Object value) {
				return transformer.viewToModel((V) value);
			}
		});

		this.setConverter(this.modelToView, new IConverter() {
			@Override
			public Object getToType() {
				return transformer.getViewType();
			}

			@Override
			public Object getFromType() {
				return transformer.getModelType();
			}

			@Override
			public Object convert(Object value) {
				return transformer.modelToView((M) value);
			}
		});

		return this;
	}

	// ********************************************************
	// ** Building
	// ********************************************************

	@Override
	public void build() {
		this.createBinding();
	}

	/**
	 * Creates the JFace data binding. Useful for adding behavior in subclasses that are aware of JFace.
	 * 
	 * @return The JFace binding object
	 */
	protected Binding createBinding() {
		return this.dbc.bindValue(view, model, viewToModel, modelToView);
	}
}
