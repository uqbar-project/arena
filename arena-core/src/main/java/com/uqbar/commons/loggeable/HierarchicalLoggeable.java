package com.uqbar.commons.loggeable;

import java.io.Serializable;

/**
 * DOCME:
 * @author npasserini
 */
public interface HierarchicalLoggeable extends Serializable {
	
	/**
	 * Se logea a traves de un objeto Logger quien visita a los Loggeables atraves de este metodo.
	 * Ellos sabran agregarse al logger. 
	 */
	public void appendYourselfTo(HierarchicalLogger visitor);
}
