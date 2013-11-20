package com.uqbar.common.transaction.Collection

import java.util.Set
import java.util.HashSet
import java.util.Collection
import java.util.HashMap
import java.util.Map
import java.util.Set;
import java.util.HashMap

/**
 * @param map
 */
class TransactionalMap[K, V](owner:Any, fieldName:String) extends TransactionalData[Map[K, V]](owner, fieldName) with Map[K, V]{
	
	def this(map:Map[K, V], owner:Any, fieldName:String) {
		this(owner, fieldName)
		setData(map);
	}

	def defauldValue = new HashMap[K, V]();

	override def containsKey(key:Object) = data.containsKey(key)
	override def containsValue(key:Object) = data.containsValue(key)
	override def get(key:Object)  = data.get(key)
	override def put(k:K, v:V)  = updateData(_.put(k, v))
	override def putAll(m:Map[_<:K, _ <:V])  = updateData(_.putAll(m))
	override def remove(o:Object)  = updateData(_.remove(o))
	override def size() = data.size();
	override def isEmpty() = data.isEmpty()
	override def clear() = updateData(_.clear())
	override def keySet() = data.keySet()
	override def values() = data.values()
	override def entrySet() = data.entrySet()
	
}

