package com.uqbar.aop.transaction;

import java.util.Map;

import com.uqbar.common.transaction.ObjectTransaction;
import com.uqbar.common.transaction.TaskOwner;

/**
 * Represents a non-transactional context.
 * 
 * @author npasserini
 * @author jfernandes
 */
public class NullObjectTransaction extends ObjectTransactionImpl {

	public NullObjectTransaction(ObjectTransactionImpl parent, TaskOwner owner, Long id) {
		super(parent, owner, id);
	}

	/**
	 * Interceptor for field reading.
	 * @return the parent transaction's value for that field, if it has a parent. Otherwise the real object's field value.
	 */
	public Object fieldRead(Object owner, String fieldName, Object value) {
		return this.getParent() != null ? this.fieldReadFromParent(owner, fieldName, value) : value;
	}

	protected Object fieldReadFromParent(Object owner, String fieldName, Object value) {
		return ((ObjectTransactionImpl) this.getParent()).fieldRead(owner, fieldName, value);
	}

	/**
	 * Interceptor for field writing. It should not be happening. You should not be modifying objects without
	 * a transaction.
	 */
	public Object fieldWrite(Object owner, String fieldName, Object newValue, Object oldValue) {
		return newValue;
	}

	public Object arrayRead(Object owner, String fieldName, Object value) {
		return value;
	}

	/**
	 * @return null, nadie puede ser padre de una NullTransaction
	 */
	protected ObjectTransaction commit() {
		this.getLogger().debug("Commit on NullTransaccion [" + this.getId() + "]");
		ObjectTransaction transaction = this.removeParent();
		this.getLogger().debug("Next transaction: [" + (transaction != null ? transaction.getId() : null) + "]");
		this.setState(ObjectTransactionImpl.STATE_COMMITED);
		return transaction;
	}

	/**
	 * Populates all values from the map to the real objects.
	 */
	protected void commitMap(Map<IdentityWrapper, Map<String, Object>> attributeMap) {
		super.populateValuesToObjects();
	}

	public void cloneAttributes(Object sourceObject, Object targetObject) {
		// nothing to be done.
	}
	
	@Override
	protected void addParentChild() {
	}
}