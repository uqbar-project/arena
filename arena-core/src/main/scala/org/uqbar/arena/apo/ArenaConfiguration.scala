package org.uqbar.arena.apo

import org.uqbar.commons.utils.TransactionalAndObservable
import com.uqbar.apo.Advice
import com.uqbar.apo.ConstructorCallInterceptor
import com.uqbar.apo.pointcut.AnnotationPointCut
import com.uqbar.apo.pointcut.ConstructorCallPointCut
import com.uqbar.apo.pointcut.FieldPointCut
import com.uqbar.apo.pointcut.PointCut
import com.uqbar.poo.aop.ObservableConfiguration
import com.uqbar.pot.aop.TransactionalConfiguration
import javassist.CtClass
import com.uqbar.apo.MethodInterceptor
import com.uqbar.apo.pointcut.MethodPointCut
import com.uqbar.apo.ConstructorInterceptor
import com.uqbar.apo.pointcut.InterceptMatchPointCut
import javassist.CtConstructor
import org.uqbar.commons.utils.Observable
import org.uqbar.commons.utils.Transactional
import com.uqbar.apo.parser.APOParser
import com.uqbar.apo.FieldInterceptor
/**
 *
 * @author nny
 */

class ArenaConfiguration extends ObservableConfiguration with TransactionalConfiguration {

  override def createAdvices(): List[Advice] = {
    val pointCut = new PointCut with AnnotationPointCut with FieldPointCut {
      noStatic &&
      fieldName(!_.toLowerCase().contains("changesupport"))
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

