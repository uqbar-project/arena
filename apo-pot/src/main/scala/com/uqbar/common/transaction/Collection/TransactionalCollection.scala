package com.uqbar.common.transaction.Collection;

import java.util.Collection

/**
 * @author nnydjesus
 * @param <D>
 * @param <E>
 */
abstract class TransactionalCollection[D<: Collection[E], E] extends TransactionalData[D] with Collection[E] {
	
	override def size() = data.size();
	override def isEmpty() = data.isEmpty()
	override def contains(o:Object) = data.contains(o);
	override def containsAll(c:Collection[_]) = data.containsAll(c);
	override def iterator() = data.iterator()
	override def toArray() = data.toArray()
	override def toArray[T](a:Array[T with java.lang.Object]) = null
	override def add(e:E) = updateData(_.add(e))
	override def addAll(c:Collection[_ <:E]) = updateData(_.addAll(c))
	override def remove(e:Object) = updateData(_.remove(e))
	override def removeAll(c:Collection[_]) = updateData(_.removeAll(c))
	override def retainAll(c:Collection[_]) = updateData(_.retainAll(c))
	override def clear() = updateData(_.clear())
}
