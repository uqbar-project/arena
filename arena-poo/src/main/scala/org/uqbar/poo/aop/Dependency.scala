package org.uqbar.poo.aop

case class Dependency(property: String, dependencies: List[String]) {
  def toStatement = s"""org.uqbar.commons.model.ObservableUtils.dependencyOf(this, "${property}", new String[] { ${dependenciesVarargs} } );"""
  private def dependenciesVarargs = dependencies map { it => s""""$it"""" } mkString (",")
}