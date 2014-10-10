package com.uqbar.aop.transaction;

import javassist.expr.Instanceof;

/**
 * Clase util para wrapear un objeto cuyo hashcode es mutable, para que se comporte por identidad.
 * 
 * @author jpicasso
 */
public class IdentityWrapper {

    /** decoratee * */
    private Object key;

    // *****************
    // ** Constructores
    // *****************
    private IdentityWrapper(Object key) {
	this.key = key;
    }

    //***************************
    //** Helper
    //***************************
    public static IdentityWrapper wrapKey(Object wrapee) {
	return new IdentityWrapper(wrapee);
    }

    // ***************************
    // ** Accessors
    // ***************************
    public Object getKey() {
	return this.key;
    }

    // ***************************
    // ** #Object
    // ***************************
    /**
     * @return System.identityHashcode de la entidad decorada.
     */
    public int hashCode() {
	return System.identityHashCode(this.key);
    }

    /**
     * @return el resutlado de comparar las key decoradas por identidad.
     */
    public boolean equals(Object obj) {
	// Se evalua la igualdad por referencia
	    if(obj instanceof IdentityWrapper){
	    	return this.key == ((IdentityWrapper) obj).key;
	    }
	    return false;
    }

    public String toString() {
	return this.key.toString();
    }
}