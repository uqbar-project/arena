package org.uqbar.arena.aop.potm

import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.uqbar.aop.transaction.ObjectTransactionManager
import org.uqbar.aop.transaction.IdentityWrapper
import org.uqbar.commons.model.annotations.Observable

@Observable
class MonitorApplicationModel {

  val parent = new ObjectTransactionImplObservable(ObjectTransactionManager.getTransactionRegistry().get(0L))
  var transaction: ObjectTransactionImplObservable = _
  var tableResult = Buffer[Entry]()
  var listResult: java.util.List[Any] = _
  var transactionalObject:IdentityWrapper = _

  def getParent() = parent
  def getChildren(): java.util.List[ObjectTransactionImplObservable] = List(parent)

  def getTransaction() = this.transaction
  def setTransaction(transaction: ObjectTransactionImplObservable) {
    this.transaction = transaction
    this.listResult = attributeMap.keys.toList
    this.transactionalObject = null
    this.tableResult = Buffer[Entry]()
  }
  
  def getTransactionalObject() = this.transactionalObject
  def setTransactionalObject(objec: IdentityWrapper) {
    transactionalObject = objec
    if(attributeMap.containsKey(transactionalObject)){
    	tableResult = attributeMap(transactionalObject).entrySet.map(entry =>{
    		new Entry(entry.getKey, entry.getValue, transactionalObject.getKey)
    	}).toBuffer
    }else{
    	tableResult = Buffer()
    }
  }

  def attributeMap = transaction.objectTransaction.attributeMap
  def getTableResult() = tableResult.asJava
  def setTableResult(tableResult: Buffer[Entry]) = this.tableResult = tableResult
  def getListResult() = listResult
  def setListResult[A](listResult: List[A]) = this.listResult = listResult.toList;

}