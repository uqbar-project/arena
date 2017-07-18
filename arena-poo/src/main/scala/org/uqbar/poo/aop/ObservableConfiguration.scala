package org.uqbar.poo.aop

import org.uqbar.apo.Advice
import org.uqbar.apo.Configuration
import org.uqbar.apo.pointcut.AnnotationPointCut
import org.uqbar.apo.pointcut.FieldPointCut
import org.uqbar.apo.pointcut.PointCut
import org.uqbar.commons.model.annotations.Observable

import javassist.CtClass

/**
 *
 * @author nny
 */
trait ObservableConfiguration extends Configuration {
  val observableFieldInterceptor = new ObservableFieldInterceptor()
  val observableBehavior = new ObservableBehavior()

  override def createAdvices(): List[Advice] = {
    val fieldPoint = new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[Observable].getName())
    }

    val observableAdvice = new Advice(fieldPoint, observableFieldInterceptor) {
      override def apply(ctClass: CtClass) {
        observableBehavior.addBehavior(ctClass)
        super.apply(ctClass)
      }
    }

    super.createAdvices().::(observableAdvice)
  }
}

class ObservableConfigurationImpl extends ObservableConfiguration
