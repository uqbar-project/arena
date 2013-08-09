package org.uqbar.arena.apo

import org.uqbar.commons.utils.TransactionalAndObservable

import com.uqbar.apo.Advice
import com.uqbar.apo.pointcut.PointCut
import com.uqbar.apo.pointcut.AnnotationPointCut
import com.uqbar.pot.aop.TransactionalConfiguration
import com.uqbar.apo.pointcut.FieldPointCut
import javassist.CtClass
import com.uqbar.poo.aop.ObservableConfiguration
import com.uqbar.apo.APODSL._
import com.uqbar.apo.pointcut.MethodPointCut
import com.uqbar.apo.pointcut.ClassPointCut
import javassist.ClassPool
import com.uqbar.poo.aop.ObservableConfiguration
/**
 *
 * @author nny
 */

class ArenaConfiguration extends ObservableConfiguration with TransactionalConfiguration {

  override def createAdvices(): List[Advice] = {
    val pointCut = new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[TransactionalAndObservable].getName())
    }

    val OandTAdvice = new Advice(pointCut) {
      override def apply(ctClass: CtClass) {
        observableBehavior.addBehavior(ctClass)
        super.apply(ctClass)
      }

    }

    OandTAdvice
      .addInterceptor(observableFieldInterceptor)
      .addInterceptor(transactionInterceptor)
      
    super.createAdvices().::(OandTAdvice)
  }

}
