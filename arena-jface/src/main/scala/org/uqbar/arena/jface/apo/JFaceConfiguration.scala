package org.uqbar.arena.jface.apo
import org.uqbar.apo.Configuration
import org.uqbar.apo.APODSL._
import org.uqbar.apo.pointcut.PointCut
import org.uqbar.apo.pointcut.ClassPointCut
import org.uqbar.apo.Advice
import org.uqbar.apo.pointcut.MethodPointCut
import javassist.ClassPool

class JFaceConfiguration extends Configuration {

  override def createAdvices(): List[Advice] = {
    val ScalaBeanInfoFQN = "org.uqbar.commons.model.utils.ScalaBeanInfo"
    var interceptor = method.around((s, m) => {
      var etype = ClassPool.getDefault().get("org.eclipse.core.databinding.BindingException")
      m.addCatch("{return " + ScalaBeanInfoFQN +".getPropertyDescriptor($1, $2);}", etype)
    })

    val instrospectorPoint = new PointCut with ClassPointCut with MethodPointCut {
      className(_.equals("BeansObservables")) && packageName(_.equals("org.eclipse.core.databinding.beans"))

      methodName(_.equals("getPropertyDescriptor"))
    }
    super.createAdvices().::(new Advice(instrospectorPoint, interceptor))
  }
  
}