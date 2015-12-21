package org.uqbar.poo.aop

import org.uqbar.apo.pointcut.AnnotationPointCut
import org.uqbar.apo.pointcut.FieldPointCut

import javassist.CtClass

import org.uqbar.apo.pointcut.PointCut
import org.uqbar.apo.Advice
import org.uqbar.apo.Configuration
import org.uqbar.commons.utils.Observable

import javassist.Modifier

import org.uqbar.apo.pointcut.ClassPointCut
import org.uqbar.apo.pointcut.MethodPointCut
import org.uqbar.commons.utils.ReflectionUtils

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
