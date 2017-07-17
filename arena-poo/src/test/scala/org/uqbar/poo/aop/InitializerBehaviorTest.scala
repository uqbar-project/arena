package org.uqbar.poo.aop

import scala.beans.BeanProperty

import org.junit.Assert
import org.junit.Test
import org.uqbar.commons.model.annotations.Dependencies
import org.uqbar.commons.model.annotations.Observable
import javassist.ClassPool

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
      AOPConfiguration.ObservableUtilFQN + ".dependencyOf(this, \"foo\", new String[] { \"bar\" } );",
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

