package com.uqbar.apo
import javassist.expr.FieldAccess
import javassist.expr.Expr
import javassist.CtBehavior
import com.uqbar.apo.parser.APOParser
import javassist.CannotCompileException

abstract class Behavior[E](var call: (E) => String) {
  def proceed(statement: StringBuffer, e: E) = {
    try
    	doProceed(statement, e)
    catch {
      case ex: CannotCompileException =>
        throw new APOException(s"Error while processing statement $statement with expression $e", ex)
    }
  }
  
  def doProceed(statement: StringBuffer, e: E)

  def parse(code: String, e: E) = APOParser.parse(e, code)
}

case class Before[E <: CtBehavior](f: (E) => String) extends Behavior[E](f) {
  def doProceed(statement: StringBuffer, e: E) = e.insertBefore(parse(call(e), e))
}

case class After[E <: CtBehavior](f: (E) => String) extends Behavior[E](f) {
  def doProceed(statement: StringBuffer, e: E) = e.insertAfter(parse(call(e), e))
}

object Around {
  def apply[E](edit: (StringBuffer, E) => Unit) = new Around(edit)
}
class Around[E](edit: (StringBuffer, E) => Unit) extends Behavior[E](null) {
  def doProceed(statement: StringBuffer, e: E) = edit(statement, e)
}

case class Read[T](f: (StringBuffer, T) => Unit) extends Around[T](f)
case class Write[T](f: (StringBuffer, T) => Unit) extends Around[T](f)
