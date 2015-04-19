package com.uqbar.apo

object APODSL {
  def method = new Object with MethodInterceptor
  def methodCall = new Object with MethodCallInterceptor
  def field = new Object with FieldInterceptor

}