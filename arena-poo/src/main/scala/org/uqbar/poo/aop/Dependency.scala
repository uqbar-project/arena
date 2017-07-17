package org.uqbar.poo.aop

object AOPConfiguration {
	val ObservableUtilFQN : String = "org.uqbar.commons.model.utils.ObservableUtils"
}

case class Dependency(property: String, dependencies: List[String]) {
  def toStatement = s"""${AOPConfiguration.ObservableUtilFQN}.dependencyOf(this, "${property}", new String[] { ${dependenciesVarargs} } );"""
  private def dependenciesVarargs = dependencies map { it => s""""$it"""" } mkString (",")
}