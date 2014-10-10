package org.uqbar.arena.examples.controls.spinner;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

import org.uqbar.commons.utils.Observable;

/**
 * 
 * @author jfernandes
 */
@Observable
public class Alarm {
	private int defCon;
	private int annotatedDefCon; 

	public int getDefCon() {
		return this.defCon;
	}

	public void setDefCon(int defCon) {
		this.defCon = defCon;
	}

	@Min(value = 1)
	@Max(value = 5)
	public int getAnnotatedDefCon() {
		return this.annotatedDefCon;
	}
	
	public void setAnnotatedDefCon(int annotatedDefCon) {
		this.annotatedDefCon = annotatedDefCon;
	}

}
