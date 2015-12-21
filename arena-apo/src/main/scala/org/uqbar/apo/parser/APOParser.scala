package org.uqbar.apo.parser

import scala.collection.JavaConversions._
import scala.util.parsing.combinator.syntactical.StandardTokenParsers
import org.reflections.Reflections
import javassist.expr.FieldAccess
import javassist.CtPrimitiveType
import org.uqbar.aop.util.ReflectionLibraryUtil

object APOParser {

  def parse(string: String): String = parse(null, string)

  def parse(fieldAccess: Any, string: String): String = {
    var result = string;

    ReflectionLibraryUtil.getSubTypesOf(classOf[Token[_]]).foreach(subtype => {
      val token: Token[Any] = Class.forName(subtype).newInstance().asInstanceOf[Token[Any]]
      if (result.contains(token.getClass().getSimpleName())) {
        result = result.replace(token.getClass().getSimpleName(), token(fieldAccess));
      }
    })
    return result;
  }
}
