package org.uqbar.arena.aop.windows

/**
 * Trait que reemplaza al MessageSender hecho en Java: su misión es evitar
 * repetir código que invoca por reflection un método de un objeto sin
 * definir un objeto que luego quiera ser usado para implementar una Action
 * (a partir de la versión 3.6.1 de Arena buscamos que las acciones como el 
 * onClick de un botón estén dentro de un closure forzando así la validación
 * del mensaje que se envía)
 * 
 * author dodain
 * 
 */
trait ActionExecuter {

  def call(target: Object, methodName: String) = target.getClass.getMethod(methodName).invoke(target)
  
}