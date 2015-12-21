package org.uqbar.apo
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.xml.Elem
import scala.xml.XML._
import java.io.StringReader

case class Property(var name: String, var values: ListBuffer[String]) {

  def value = values(0)
  def value(default: String): String = if (values.isEmpty) default else value
}

object APOConfig {
  val PROPERTIES_FILE = "uqbar.config.xml";
  val FILE_PREFIX = "apo";
  val FILE_SUFFIX = "enable";
  val AOP_ENABLE_KEY = "classloader";
  val AOP_CONFIG_CLASS = "apo.adviceConfiguration";

  lazy val properties: Map[String, Property] = parseConfig

  protected def isEnableWithFullKey(fullKey: String) = properties(fullKey).value("false").toBoolean

  def isEnable(shortKey: String) = isEnableWithFullKey(FILE_PREFIX + "." + shortKey + "." + FILE_SUFFIX)

  def getProperty(propertyName: String) = properties(propertyName)

  def isAOPEnable() = isEnable(AOP_ENABLE_KEY)

  def getAOPConfigClass() = properties(AOP_CONFIG_CLASS)

  protected def parseConfig = this.synchronized {
    val file = classOf[Property].getClassLoader().getResourceAsStream(PROPERTIES_FILE)
    val source = scala.xml.Source.fromInputStream(file)
    val xml = load(source)
    
    var properties = Map[String, Property]()
    (xml \ "property").foreach(property => {
      var name = (property \ "@name").toString()
      var values = ListBuffer[String]()

      (property \ "value").foreach(value => {
        values += value.text
      })
      properties(name) = Property(name, values)
    })
    properties
  }
}