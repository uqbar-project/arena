package com.uqbar.common.transaction.Collection

import java.util.Collection
import java.util.ArrayList
import java.util.List

import org.uqbar.commons.utils.Transactional

@Transactional
class TransacionalList[E](owner:Any, fieldName:String) extends TransactionalCollection[List[E], E](owner, fieldName) with List[E]{
	
	def this(list:List[E], owner:Any, fieldName:String) {
	  this(owner, fieldName)
	  this.setData(list)
	}

	override def addAll(index:Int, c:Collection[_ <:E]) = updateData(_.addAll(index, c))
	override def get(index:Int) = data.get(index)
	override def indexOf(o:Object) = data.indexOf(o)
	override def lastIndexOf(o:Object) = data.lastIndexOf(o)
	override def set(index:Int, e:E) = updateData(_.set(index, e))
	override def add(index:Int, e:E) = updateData(_.add(index, e))
	override def remove(index:Int) = updateData(_.remove(index))
	override def listIterator() = data.listIterator()
	override def listIterator(i:Int) = data.listIterator(i)
	override def subList(fromIndex:Int, toIndex:Int) = data.subList(fromIndex, toIndex)

	override def defauldValue() = new ArrayList[E]()
}
