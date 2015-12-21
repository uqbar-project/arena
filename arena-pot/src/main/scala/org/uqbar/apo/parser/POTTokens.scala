package org.uqbar.apo.parser

case class $interceptor() extends Token[Any] {
  def apply(t: Any) = $interceptor(t)
}
object $interceptor {
  def apply(t: Any) = """((org.uqbar.aop.transaction.ObjectTransactionImpl) 
		  					org.uqbar.aop.transaction.ObjectTransactionManager.getTransaction())"""
}