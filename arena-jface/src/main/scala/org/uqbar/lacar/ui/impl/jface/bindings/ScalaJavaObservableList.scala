package org.uqbar.lacar.ui.impl.jface.bindings

import scala.collection.Seq
import scala.collection.JavaConversions._
import scala.collection.mutable.Buffer
import scala.collection.Set
import scala.collection.convert.WrapAsJava
import scala.collection.Iterable

/**
 * Convierte una posibles coleccion de Scala a Java
 */
trait ScalaJavaConverter {
  def convertScalaCollectionToJavaIfNeeded(value : Object) : java.util.Collection[_]
}

class ScalaJavaListConverter extends ScalaJavaConverter {
    override def convertScalaCollectionToJavaIfNeeded(value : Object) : java.util.Collection[_] = {
    value match {
      case i: Iterable[_] => WrapAsJava.asJavaCollection(i) // creo que con este ya alcanza, no hacen falta las otras implicits 
      // already using java collections, ok!
      case c: java.util.Collection[_] => c
      // fail otherwise
      case other: Any => throw new UnsupportedOperationException(s"Cannot convert scala collection type ${other.getClass} to Java Collection")
      // null returns null
      case _ => null
    }
  }
}

      // implicit conversions
//      case s: Seq[_] => s
//      case s: Set[_] => s
//      case s: scala.collection.mutable.Seq[_] => s
//      case b: Buffer[_] => b