package com.uqbar.pot.aop

import org.uqbar.commons.utils.Transactional
import com.uqbar.apo.Advice
import com.uqbar.apo.Configuration
import com.uqbar.apo.pointcut.AnnotationPointCut
import com.uqbar.apo.pointcut.FieldPointCut
import com.uqbar.apo.pointcut.PointCut
import org.uqbar.commons.utils.Observable
import org.uqbar.commons.utils.TransactionalAndObservable

/**
 *
 * @author nny
 */
trait TransactionalConfiguration extends Configuration {

  val transactionInterceptor = new TransactionFieldInterceptor()

  override def createAdvices(): List[Advice] = {
    super.createAdvices().::(new Advice(new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[Transactional].getName()) || 
      hasAnnotation(classOf[Observable].getName())|| 
      hasAnnotation(classOf[TransactionalAndObservable].getName()) 
    })
      .addInterceptor(transactionInterceptor))
  }

}

class TransactionalConfigurationImpl extends TransactionalConfiguration
