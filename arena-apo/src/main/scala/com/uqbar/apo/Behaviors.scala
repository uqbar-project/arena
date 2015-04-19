package com.uqbar.apo
import javassist.expr.FieldAccess
import javassist.expr.Expr
import javassist.CtBehavior
import com.uqbar.apo.parser.APOParser
import javassist.CannotCompileException

abstract class Behavior[E, F](var call: (F) => String) {
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

case class Before[E <: JavassisstWraperType[F] , F](f: (F) => String) extends Behavior[E, F](f) {
  def doProceed(statement: StringBuffer, e: E) = e.toBehaviour.insertBefore(parse(call(e.content), e))
}

case class After[E <: JavassisstWraperType[F], F](f: (F) => String) extends Behavior[E, F](f) {
  def doProceed(statement: StringBuffer, e: E) = e.toBehaviour.insertAfter(parse(call(e.content), e))
}

object Around {
  def apply[E](edit: (StringBuffer, E) => Unit) = new Around(edit)
}

class Around[E <: JavassisstWraperType[F], F](edit: (StringBuffer, F) => Unit) extends Behavior[E, F](null) {
  def doProceed(statement: StringBuffer, e: E) = edit(statement, e.content)
}

case class Read[E <: JavassisstWraperType[F], F](f: (StringBuffer, F) => Unit) extends Around[E, F](f)
case class Write[E <: JavassisstWraperType[F], F](f: (StringBuffer, F) => Unit) extends Around[E, F](f)
