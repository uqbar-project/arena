package org.uqbar.poo.aop

import org.junit.Assert
import org.junit.Test
import org.uqbar.commons.utils.Dependencies
import org.uqbar.commons.utils.Observable
import org.uqbar.poo.aop.Dependency;

import javassist.ClassPool
import scala.beans.BeanProperty

class InitializerBehaviorTest {

  val pool = ClassPool.getDefault();

  @Test
  def canAddInitializersForObservableBeans() {
    val ctClass = pool.getCtClass(classOf[ObservableBean].getName);

    new InitializerBehavior {}.addDependenciesInitializer(ctClass);
    
    new ObservableBean
  }

  @Test
  def canAddInitializersForObservableBeansWithDependencies() {
    val ctClass = pool.getCtClass(classOf[ObservableBeanWithDependencies].getName);

    new InitializerBehavior {}.addDependenciesInitializer(ctClass);
    
    new ObservableBeanWithDependencies
  }

  @Test
  def generatesProperSourceCode() {
    Assert.assertEquals(
      "org.uqbar.commons.model.ObservableUtils.dependencyOf(this, \"foo\", new String[] { \"bar\" } );",
      new Dependency("foo", List("bar")).toStatement)
  }

}

@Observable
class ObservableBean {

}

@Observable
class ObservableBeanWithDependencies {
  @BeanProperty
  var bar = 0

  @Dependencies(Array("bar"))
  def getFoo() = bar + 1
}

