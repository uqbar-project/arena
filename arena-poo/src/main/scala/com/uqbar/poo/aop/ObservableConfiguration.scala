package com.uqbar.poo.aop

import com.uqbar.apo.pointcut.AnnotationPointCut
import com.uqbar.apo.pointcut.FieldPointCut
import javassist.CtClass
import com.uqbar.apo.pointcut.PointCut
import com.uqbar.apo.Advice
import com.uqbar.apo.Configuration
import org.uqbar.commons.utils.Observable
import javassist.Modifier
import com.uqbar.apo.pointcut.ClassPointCut
import com.uqbar.apo.pointcut.MethodPointCut
import org.uqbar.commons.utils.ReflectionUtils
import org.uqbar.commons.utils.Dependencies
import com.uqbar.apo.pointcut.MatchPointCut
import com.uqbar.apo.pointcut.MethodCallPointCut

/**
 * @author nny
 */
trait ObservableConfiguration extends Configuration {
  val observableFieldInterceptor = new ObservableFieldInterceptor()
  val observableBehavior = new ObservableBehavior()

  override def createAdvices(): List[Advice] = {
    val fieldPoint = new FieldPointCut with AnnotationPointCut {
      noStatic &&
      fieldName(!_.toLowerCase().contains("changesupport"))
      hasAnnotation(classOf[Observable].getName())
    }

    val observableAdvice = new Advice(fieldPoint, observableFieldInterceptor) {
      override def apply(ctClass: CtClass) {
        observableBehavior.addBehavior(ctClass)
        super.apply(ctClass)
      }
    }

    val dependencyPointcut = new MethodCallPointCut with AnnotationPointCut  with FieldInfo {
      methodName(name=> !List("tostring", "clazz", "changesupport").exists(filter=> name.toLowerCase.contains(filter))) &&
      notConstructor &&
      filter(mc=> isGetter(mc.getMethod()))
      hasAnnotation(classOf[Observable].getName())
    }
    val dependencyAdvice = new Advice(dependencyPointcut, new DependencyFieldInterceptor())

    super.createAdvices().::(dependencyAdvice).::(observableAdvice)
  }
}

class ObservableConfigurationImpl extends ObservableConfiguration
