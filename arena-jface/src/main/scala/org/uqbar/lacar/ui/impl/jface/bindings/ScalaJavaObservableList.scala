package org.uqbar.lacar.ui.impl.jface.bindings

import scala.collection.Iterable
import scala.collection.convert.WrapAsJava

/**
 * Convierte una posibles coleccion de Scala a Java
 */
trait ScalaJavaConverter {
  def convertScalaCollectionToJavaIfNeeded(value : Object) : java.util.Collection[_]
}

class ScalaJavaListConverter extends ScalaJavaConverter {
    override def convertScalaCollectionToJavaIfNeeded(value : Object) : java.util.Collection[_] = {
    value match {
      case i: Iterable[_] => WrapAsJava.asJavaCollection(i)  
      // already using java collections, ok!
      case c: java.util.Collection[_] => c
      // fail otherwise
      case other: Any => throw new UnsupportedOperationException(s"Cannot convert scala collection type ${other.getClass} to Java Collection")
      // null returns null
      case _ => null
    }
  }
}
