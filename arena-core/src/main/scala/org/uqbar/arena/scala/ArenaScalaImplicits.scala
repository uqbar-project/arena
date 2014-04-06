package org.uqbar.arena.scala

import com.uqbar.commons.collections.Transformer

/**
 * Contiene implicits utiles para usar arena desde scala
 * Asi evitar crear clases anonimas y poder usar bloques y funciones 
 * 
 * @author jfernandes
 */
object ArenaScalaImplicits {
  
  /** Convierte una funcion en un transformer   */
  implicit def closureToTransformer[I,O](closure: (I) => O) : Transformer[I,O] = {
	new Transformer[I,O] {
	    override def transform(i: I) : O = {
	      closure(i)
	    }
	}
  }

}