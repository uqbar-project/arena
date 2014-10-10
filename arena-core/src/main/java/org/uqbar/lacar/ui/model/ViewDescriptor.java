package org.uqbar.lacar.ui.model;

/**
 * Un objeto que sabe describir una vista, hablándole a un builder.
 * 
 * @param <T> El tipo de builder al que este descriptor le puede hablar.
 * 
 * @author npasserini
 */
public interface ViewDescriptor<T> {
	public void showOn(T builder);
	
	public void close();
}
