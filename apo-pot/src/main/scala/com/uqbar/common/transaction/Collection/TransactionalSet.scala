package com.uqbar.common.transaction.Collection

import java.util.Set
import java.util.HashSet

class TransactionalSet[T](owner:Any, fieldName:String) extends TransactionalCollection[Set[T], T](owner, fieldName) with Set[T] {

  def this(set: Set[T], owner:Any, fieldName:String) {
    this(owner, fieldName)
    setData(set);
  }

  def defauldValue = new HashSet[T]();
}
