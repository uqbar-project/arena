package org.uqbar.lacar.ui.model.bindings.collections;


/**
 * Like jface databindings IObservableCollection but with generics.
 * 
 * @author jfernandes
 */
// si extiendo de collection me molesta para las de Scala
// podria ser algo mas generico como "ObservableContainer" ?
public interface ObservableContainer {
	
	public void addChangeListener(ChangeListener listener);

	public void removeChangeListener(ChangeListener listener);

}
