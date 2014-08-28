package com.uqbar.aop.transaction.utils;

import com.uqbar.common.transaction.ObjectTransaction;
import com.uqbar.common.transaction.TaskOwner;

/**
 * @author nny
 */
public class BasicTaskOwner implements TaskOwner {
	private String theName;
	private ObjectTransaction transaction;
	
	public BasicTaskOwner(String theName) {
		super();
		this.theName = theName;
	}
	
	@Override
	public String getName() {
		return this.theName;
	}
	
	@Override
	public ObjectTransaction getTransaction() {
		return this.transaction;
	}

	@Override
	public boolean isTransactional() {
		return true;
	}

	@Override
	public void setTransaction(ObjectTransaction transaction) {
		this.transaction = transaction;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
