package com.uqbar.apo.parser

case class $interceptor extends Token[Any] {
  def apply(t: Any) = $interceptor(t)
}
object $interceptor {
  def apply(t: Any) = """((com.uqbar.aop.transaction.ObjectTransactionImpl) 
		  					com.uqbar.aop.transaction.ObjectTransactionManager.getTransaction())"""
}