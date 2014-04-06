package org.uqbar.lacar.ui.impl.jface;


import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.internal.databinding.swt.SWTProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.impl.jface.swt.observables.ControlObservableValue;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;

import com.uqbar.commons.collections.Transformer;

/**
 * 
 * @author npasserini
 */
public abstract class JFaceControlBuilder<T extends Control> extends JFaceWidgetBuilder<T> implements ControlBuilder {
	private int width = SWT.DEFAULT;
	private int heigth = SWT.DEFAULT;

	public JFaceControlBuilder(JFaceContainer container) {
		super(container);
	}

	public JFaceControlBuilder(JFaceContainer container, T jfaceWidget) {
		super(container, jfaceWidget);
	}

	// ********************************************************
	// ** Bindings
	// ********************************************************

	@Override
	public BindingBuilder observeEnabled() {
		return new JFaceBindingBuilder(this, observeEnabled(this.getWidget()));
	}
	
	@Override
	public <M, U> BindingBuilder observeBackground(Transformer<M, U> transformer) {
		return new JFaceBindingBuilder(this, new ControlObservableValue<M, U>(this.getWidget(), SWTProperties.BACKGROUND, transformer));
	}

	protected ISWTObservableValue observeEnabled(T t) {
		return t instanceof Text ? SWTObservables.observeEditable(t) : SWTObservables.observeEnabled(t);
	}

	@Override
	public BindingBuilder observeVisible() {
		return new JFaceBindingBuilder(this, SWTObservables.observeVisible(this.getWidget()));
	}

	// ********************************************************
	// ** Low level binding methods
	// ********************************************************

	/**
	 * Utilizad para simplificar la construcción bindings de bajo nivel en forma manual.
	 * 
	 * ATENCIÓN: Esto debe usarse sólo en casos que realmente ameriten la programación a bajo nivel, la forma
	 * preferida de agregar un binding es utilizando los métodos <code>#observeXXX</code> que se encuentran en
	 * las interfaces que dependen de {@link WidgetBuilder} y que devuelven un {@link BindingBuilder} que
	 * permite configurar un binding sin necesidad de escribir código dependiente de la tecnología.
	 * 
	 * @param model
	 * @param view
	 */
	public void bind(IObservableValue model, IObservableValue view) {
		new JFaceBindingBuilder(this, view, model).build();
	}

	@Override
	public void pack() {
		configureLayoutData();
		super.pack();
	}

	protected void configureLayoutData() {
		if(this.getWidget().getParent().getLayout() instanceof  GridLayout){
			GridData layoutData = new GridData(GridData.FILL);
			layoutData.grabExcessHorizontalSpace = true;
			layoutData.widthHint = width;
			layoutData.heightHint = heigth;
			this.getWidget().setLayoutData(layoutData);
		}
		
		if(this.getWidget().getParent().getLayout() instanceof  RowLayout){
			RowData layoutData = new RowData(width, heigth);
			this.getWidget().setLayoutData(layoutData);
		}

	}
	
	protected Control getControlLayout(){
		return getWidget();
	}


	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	@Override
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
}
