package org.uqbar.arena.aop.potm
import org.uqbar.aop.transaction.ObjectTransactionManager
import org.uqbar.common.transaction.ObjectTransaction
import org.uqbar.aop.transaction.ObjectTransactionImpl
import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.uqbar.commons.model.annotations.Observable

@Observable
class ObjectTransactionImplObservable(var objectTransaction:ObjectTransactionImpl){
	
	var transactions:java.util.List[ObjectTransactionImplObservable] = Buffer[ObjectTransactionImplObservable](this)
	
	def this (objectTransaction:ObjectTransaction) {
		this(objectTransaction match{
		  case obTransactionImp:ObjectTransactionImpl => obTransactionImp
		});
	}
	
	def this() {
		this(ObjectTransactionManager.getTransaction());
	}
	
	def getObjectTransaction() = objectTransaction

	def setObjectTransaction(objectTransaction:ObjectTransactionImpl ) = this.objectTransaction = objectTransaction
	
	def getIdentityWrapper() = this.objectTransaction.attributeMap.keySet.toList

	def getChildren():java.util.List[ObjectTransactionImplObservable] = {
		if(objectTransaction != null) convertToModel(objectTransaction.children) else List()
	}
	
	def getId() = objectTransaction.getId()
	
	def getParent() = new ObjectTransactionImplObservable(objectTransaction.parent)
	
	def getTransactions()  = this.transactions
	def setTransactions(list:java.util.List[ObjectTransactionImplObservable]) = this.transactions = list
	
	def convertToModel(list:java.util.List[ObjectTransactionImpl]) = list.map(o => new ObjectTransactionImplObservable(o))
	
	override def toString() = s"ObjectTransaction: ${objectTransaction.getId}"
}
