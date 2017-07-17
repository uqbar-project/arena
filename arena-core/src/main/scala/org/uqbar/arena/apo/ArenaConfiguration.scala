package org.uqbar.arena.apo

import org.uqbar.apo.Advice
import org.uqbar.apo.ConstructorCallInterceptor
import org.uqbar.apo.pointcut.AnnotationPointCut
import org.uqbar.apo.pointcut.ConstructorCallPointCut
import org.uqbar.apo.pointcut.FieldPointCut
import org.uqbar.apo.pointcut.PointCut
import org.uqbar.poo.aop.ObservableConfiguration
import org.uqbar.pot.aop.TransactionalConfiguration
import javassist.CtClass
import org.uqbar.apo.MethodInterceptor
import org.uqbar.apo.pointcut.MethodPointCut
import org.uqbar.apo.ConstructorInterceptor
import org.uqbar.apo.pointcut.InterceptMatchPointCut
import javassist.CtConstructor
import org.uqbar.apo.parser.APOParser
import org.uqbar.apo.FieldInterceptor
import org.uqbar.commons.model.annotations.TransactionalAndObservable
import org.uqbar.commons.model.annotations.Observable

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
      
   val initPointCut = new PointCut with AnnotationPointCut with FieldPointCut{
      ||(hasAnnotation(classOf[TransactionalAndObservable].getName())) || hasAnnotation(classOf[Observable].getName())
      constructor()
    }

    super.createAdvices().::(OandTAdvice).::(new Advice(initPointCut, new FieldInterceptor{}){
	      override def apply(ctClass: CtClass) {
	        ctClass.getConstructors().foreach(c=>{
	          c.insertAfter(APOParser.parse(c, "$interceptor.setPropertyValuesAfterInit($this);"))
	        })
	        super.apply(ctClass)
	      }
    })
  }
}

