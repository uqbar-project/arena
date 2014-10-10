package com.uqbar.aop.transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.uqbar.common.transaction.ObjectTransaction;
import com.uqbar.common.transaction.TaskOwner;
import com.uqbar.common.transaction.Collection.TransactionalData;

/**
 * Represents a non-transactional context.
 * 
 * @author npasserini
 * @author jfernandes
 */
class NullObjectTransaction(parent:ObjectTransactionImpl, owner:TaskOwner, id:Long) extends ObjectTransactionImpl(parent, owner, id){

	/**
	 * Interceptor for field reading.
	 * @return the parent transaction's value for that field, if it has a parent. Otherwise the real object's field value.
	 */
	override def fieldRead(owner:Any, fieldName:String , value:Any):Any= {
		if(this.parent != null) this.fieldReadFromParent(owner, fieldName, value) else value
	}

	protected def fieldReadFromParent(owner:Any, fieldName:String , value:Any):Any= {
		return parent.fieldRead(owner, fieldName, value)
	}

	/**
	 * Interceptor for field writing. It should not be happening. You should not be modifying objects without
	 * a transaction.
	 */
	override def doFieldWrite[T](owner:Object, fieldName:String, newValue:T, oldValue:T): T={
		return newValue;
	}

	/**
	 * @return null, nadie puede ser padre de una NullTransaction
	 */
	override protected def commit():ObjectTransaction = {
		logger.debug("Commit on NullTransaccion [" + this.getId() + "]");
		val transaction = this.removeParent();
		logger.debug("Next transaction: [" + (if(transaction != null) transaction.getId() else null) + "]");
		state = ObjectTransactionImpl.STATE_COMMITED
		transaction;
	}

	/**
	 * Populates all values from the map to the real objects.
	 */
	protected def commitMap(attributeMap:Map[IdentityWrapper, Map[String, Any]]) {
		super.populateValuesToObjects();
	}

	override def cloneAttributes(sourceObject:Object, targetObject:Object) {	}
	
	override protected def addParentChild(){}
}