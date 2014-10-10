package org.uqbar.arena.examples.controls.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton a efectos de simplificar el ejemplo ya que 
 * no tiene persistencia.
 * 
 * @author jfernandes
 */
public class Restaurant {
	private static Restaurant instance;
	private List<Entrada> entradas = new ArrayList<Entrada>();
	private List<Bebida> bebidas = new ArrayList<Bebida>();
	private List<Plato> platos = new ArrayList<Plato>();
	
	
	public Restaurant() {
		entradas.add(new Entrada("Ensalada Mixta"));
		entradas.add(new Entrada("Matambrito"));
		entradas.add(new Entrada("Vitel Tone"));
		
		bebidas.add(new Bebida("Gaseosa"));
		bebidas.add(new Bebida("Vino"));
		bebidas.add(new Bebida("Cerveza"));
		bebidas.add(new Bebida("Agua"));
		
		platos.add(new Plato("Milanesa c/fritas"));
		platos.add(new Plato("Asado"));
		platos.add(new Plato("Ensalada Cesar"));
		platos.add(new Plato("Goulash"));
	}
	
	public static Restaurant getInstance() {
		if (instance == null) {
			instance = new Restaurant();
		}
		return instance;
	}
	
	public List<Bebida> getBebidas() {
		return bebidas;
	}
	
	public List<Entrada> getEntradas() {
		return entradas;
	}
	
	public List<Plato> getPlatos() {
		return platos;
	}

}
