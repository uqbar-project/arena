package com.uqbar.apo

import javassist.ClassPool
import javassist.CtClass

/**
 * Colabora con el FrameworkClassLoader para construir los Aspectos que fueron declarados.
 *
 */

class AdviceWeaver {
  var classPool: ClassPool = _
  var advices: List[Advice] = List[Advice]()

  def this(cp: ClassPool) {
    this()
    this.classPool = cp
  }
  
  def init()= initXML()

  def initXML() {
    val configProperty = APOConfig.getAOPConfigClass()
    configProperty.values.foreach(configClass => {
      val configurationClass: Class[Configuration] =  this.getClass().getClassLoader().loadClass(configClass).asInstanceOf[Class[Configuration]]
      val adviceConfiguration = configurationClass.newInstance()
      this.advices  = this.advices ++ adviceConfiguration.createAdvices()
    })
  }

  def applyAdvice(ctClass: CtClass) {
    if (!ctClass.isFrozen()) {
      this.advices.foreach(advice => {
        if (advice.pointCut.evaluate(ctClass)) {
          applyAdviceToCtClass(ctClass, advice);
        }
      })
    }
  }

  def applyAdviceToCtClass(ctClass: CtClass, advice: Advice) {
    advice.apply(ctClass)
    //    ctClass.getFields().foreach(f=> advice.apply(f.getType()))

  }
}