package org.uqbar.arena.examples.controls.multiobjectcreation;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.ObservableObject;

/**
 * 
 * @author jfernandes
 */
public class UbicarCosa extends ObservableObject {
	private String posicion;
	private Ubicable ubicable;
	private List<Ubicable> ubicables = new ArrayList<Ubicable>();

	public UbicarCosa() {
		this.ubicables.add(this.ubicable = new Esfera());
		this.ubicables.add(new Personaje());
	}
	
	public String getPosicion() {
		return posicion;
	}
	
	public void setPosicion(String posicion) {
		setFieldValue("position", posicion);
	}
	
	public Ubicable getUbicable() {
		return ubicable;
	}

	public void setUbicable(Ubicable ubicable) {
		setFieldValue("ubicable", ubicable);
	}
	
	public List<Ubicable> getUbicables() {
		return this.ubicables;
	}

}
