package org.uqbar.arena.bindings.collections

import scala.collection.generic.Growable
import org.uqbar.lacar.ui.model.bindings.collections.ChangeListener
import scala.collection.mutable.ArrayBuffer
import org.uqbar.lacar.ui.model.bindings.collections.ObservableContainer
import scala.collection.generic.Shrinkable
import scala.collection.mutable.SeqLike
import scala.collection.mutable.Seq
import scala.collection.mutable.BufferLike
import scala.collection.mutable.Buffer
//
// Extended scala traits with support for observability.
// All methods that change the objects are intercepted through the 
// Abstract Trait Pattern to fire up notifications.
// 
//  @author jfernandes
//

object Observables {
  type ObservableBufferLike[A] = TObservableContainer with ObservableGrowable[A] with ObservableShrinkable[A]
}

trait ObservableTrait {
  def fireAround[R](code: () => R): R = {
    val r = code()
    fireChanged()
    return r
  }
  
  def fireChanged()
}

trait TObservableContainer extends ObservableContainer {
  var listeners = new ArrayBuffer[ChangeListener]

  override def addChangeListener(listener: ChangeListener) {
    listeners += listener
  }

  override def removeChangeListener(listener: ChangeListener) {
    listeners -= listener
  }

  def fireChanged() {
    listeners foreach (_ handleChange)
  }

}

trait ObservableGrowable[A] extends Growable[A] with ObservableTrait {
  // intercepted, delegated and fired change
  abstract override def +=(elem: A) = fireAround { () => super.+=(elem) }
  abstract override def +=(elem1: A, elem2: A, elems: A*) = fireAround { () => super.+=(elem1, elem2) }
  abstract override def ++=(xs: TraversableOnce[A]) = fireAround { () => super.++=(xs) }
  abstract override def clear(): Unit = fireAround { () => super.clear() }
}

trait ObservableShrinkable[A] extends Shrinkable[A] with ObservableTrait {
  abstract override def -=(elem: A) = fireAround { ()=> super.-=(elem) }
  abstract override def --=(xs: TraversableOnce[A]) = fireAround { ()=> super.--=(xs) }
}

trait ObservableSeqLike[A, +This <: ObservableSeqLike[A, This] with Seq[A]] 
		extends SeqLike[A,This] with ObservableTrait  {
  self : This =>
  
	abstract override def update(idx: Int, elem: A) = {
	  fireAround { () => super.update(idx, elem) }
	}
	abstract override def transform(f: A => A) = fireAround {()=> super.transform(f) }
}  
