package org.uqbar.pot.aop

import org.uqbar.commons.utils.Transactional

import org.uqbar.apo.Advice
import org.uqbar.apo.Configuration
import org.uqbar.apo.pointcut.AnnotationPointCut
import org.uqbar.apo.pointcut.FieldPointCut
import org.uqbar.apo.pointcut.PointCut

/**
 *
 * @author nny
 */
trait TransactionalConfiguration extends Configuration {

  val transactionInterceptor = new TransactionFieldInterceptor()

  override def createAdvices(): List[Advice] = {
    super.createAdvices().::(new Advice(new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[Transactional].getName())
    })
      .addInterceptor(transactionInterceptor))
  }

}

class TransactionalConfigurationImpl extends TransactionalConfiguration
