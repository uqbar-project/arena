package org.uqbar.arena.examples.controls;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Just an implementation to check Arena exception against a non observable view model 
 */
public class FacturaNoObservable {

	Date fecha;
	BigDecimal saldo;
	
	/**
	 **********************************************************
	 * GETTERS & SETTERS
	 **********************************************************
	 */
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
}
