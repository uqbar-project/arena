package org.uqbar.apo

object APODSL {
  def method = new Object with MethodInterceptor
  def field = new Object with FieldInterceptor

}