package org.uqbar.arena.jface.apo
import com.uqbar.apo.Configuration
import com.uqbar.apo.APODSL._
import com.uqbar.apo.pointcut.PointCut
import com.uqbar.apo.pointcut.ClassPointCut
import com.uqbar.apo.Advice
import com.uqbar.apo.pointcut.MethodPointCut
import javassist.ClassPool

class JFaceConfiguration extends Configuration {

  override def createAdvices(): List[Advice] = {
    var inteceptor = method.around((s, m) => {
      var etype = ClassPool.getDefault().get("org.eclipse.core.databinding.BindingException")
      m.addCatch("{return org.uqbar.commons.model.ScalaBeanInfo.getPropertyDescriptor($1, $2);}", etype)
    })

    val instroperctorPoint = new PointCut with ClassPointCut with MethodPointCut {
      className(_.equals("BeansObservables")) && packageName(_.equals("org.eclipse.core.databinding.beans"))

      methodName(_.equals("getPropertyDescriptor"))
    }
    super.createAdvices().::(new Advice(instroperctorPoint, inteceptor))
  }
  
}