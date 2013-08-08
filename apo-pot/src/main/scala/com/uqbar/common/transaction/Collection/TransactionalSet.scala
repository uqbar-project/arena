package com.uqbar.common.transaction.Collection

import java.util.Set
import java.util.HashSet

class TransactionalSet[T] extends TransactionalCollection[Set[T], T] with Set[T] {

  def this(set: Set[T]) {
    this()
    setData(set);
  }

  def defauldValue = new HashSet[T]();
}
