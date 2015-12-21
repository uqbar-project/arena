package org.uqbar.apo

import java.io.InputStream
import javassist.ClassPool
import javassist.CtClass
import javassist.NotFoundException
import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

/**
 * Nuestro classloader, que al cargar una clase, le hace weaving para meterle la magia de aspectos.
 *
 * Parametro para correr tests con este ClassLoader.
 *
 * -Djava.system.class.loader=org.uqbar.apo.APOClassLoader
 *
 * @author nny
 * @author jfernandes
 * @author npasserini
 */
class APOClassLoader(parent: ClassLoader, cp: ClassPool) extends ClassLoader(parent) {
  val EXCLUDE_PACKAGE_KEY = "apo.exclude.package"
  lazy val defaultNotDefinedPackages = List("java", "javax", "sun", "com.sun", "org.w3c", "org.xml", "org.apache", "net.sf", "scala")
  lazy val notDefinedPackages = APOConfig.getProperty(EXCLUDE_PACKAGE_KEY).values
  var classPool: ClassPool = _
  var adviceWeaver: AdviceWeaver = _

  /**
   * Creates a new class loader using the specified parent class loader for delegation.
   * @param parent the parent class loader.
   * @param cp the source of class files.
   * @param adviceWeaverClass2
   */
  def this(parent: ClassLoader) {
    this(parent, ClassPool.getDefault())
    this.init(cp)
  }

  def init(cp: ClassPool) {
    this.classPool = cp
  }

  // ***************************
  // ** Metodos
  // ***************************

  /**
   * Requests the class loader to load a class.
   */
  override def loadClass(name: String, resolve: Boolean): Class[_] = {
    var c = this.findLoadedClass(name)
    if (c == null) {
      c = this.loadClassByDelegation(name)
    }
    if (c == null) {
      c = this.findClass(name)
    }
    if (c == null) {
      c = this.delegateToParent(name)
    }
    if (resolve) {
      this.resolveClass(c)
    }
    return c
  }

  override def loadClass(name: String) = super.loadClass(name)

  /**
   * Finds the specified class using <code>ClassPath</code>. If the source throws an exception, this returns
   * null.
   *
   * <p>
   * This method can be overridden by a subclass of <code>Loader</code>. Note that the overridden method
   * must not throw an exception when it just fails to find a class file.
   *
   * @return null if the specified class could not be found.
   * @throws ClassNotFoundException if an exception is thrown while obtaining a class file.
   */
  override def findClass(name: String): Class[_] = {
    var classfile: Array[Byte] = this.applyAPO(name)

    var i = name.lastIndexOf('.')
    if (i != -1) {
      var pname = name.substring(0, i)
      if (this.getPackage(pname) == null) {
        this.definePackage(pname, null, null, null, null, null, null, null)
      }
    }

    if (classfile != null) {
      this.defineClass(name, classfile, 0, classfile.length)
    } else {
      null
    }

  }

  protected def applyAPO(name: String): Array[Byte] = {
    var classfile: Array[Byte] = null
    if (this.classPool != null) {
      try {
        var ctClass = this.classPool.get(name)
        this.weaver.applyAdvice(ctClass)
        classfile = ctClass.toBytecode
      } catch {
        case e: NotFoundException => return null
      }
    }
    classfile
  }

  /**
   * The swing components must be loaded by a system class loader. javax.swing.UIManager loads a (concrete)
   * subclass of LookAndFeel by a system class loader and cast an instance of the class to LookAndFeel for
   * (maybe) a security reason. To avoid failure of type conversion, LookAndFeel must not be loaded by this
   * class loader.
   *
   * <b>java., javax.</b> (and probably <b>sun. and com.sun</b>) can't be loaded with a custom classloader
   * because the security manager throws a java.lang.SecurityException.
   */
  def loadClassByDelegation(name: String) = if (mustDelegate(name)) this.delegateToParent(name) else null

  def mustDelegate(name: String) = filterDelegate(name, defaultNotDefinedPackages) || filterDelegate(name, notDefinedPackages)

  def filterDelegate(name: String, packages: Seq[String]) = packages.exists(p => name.startsWith(p))

  def delegateToParent(classname: String): Class[_] = {
    var cl = this.getParent()
    return if (cl != null) cl.loadClass(classname) else this.findSystemClass(classname)
  }

  def weaver: AdviceWeaver = {
    this.synchronized {
      if (this.adviceWeaver == null) {
        this.adviceWeaver = new AdviceWeaver(this.classPool)
        this.adviceWeaver.init()
      }
    }
    this.adviceWeaver
  }

}