package org.uqbar.arena.performance
import java.util.Calendar
import com.uqbar.aop.transaction.ObjectTransactionManager
import com.uqbar.common.transaction.TaskOwner
import com.uqbar.aop.transaction.utils.BasicTaskOwner
import org.uqbar.commons.utils.TransactionalAndObservable
import org.uqbar.commons.model.UserException
import System.{ currentTimeMillis => time }
import scala.collection.mutable.ArrayBuffer

object PerformanceApp extends BasicTaskOwner("Performance test") with App {

  run

  def run() {
    val conversor = List.fill(100)(Conversor())
    val conversorT = List.fill(100)(ConversorTransaccional())

    executeAndCommit(code(conversorT), "Conversor Transaccional Con commit")
    execute(code(conversorT), "Conversor Transaccional Sin Transacciones")
    executeAndRollback(code(conversorT), "Conversor Transaccional Con Rollback")

    println

    execute(code(conversor), "Conversor Sin Transacciones")

    println("de aca!!")

    execute(code(conversorT), "Conversor Transaccional Sin Transacciones")
    executeAndCommit(code(conversorT), "Conversor Transaccional Con commit")
    executeAndRollback(code(conversorT), "Conversor Transaccional Con Rollback")
    execute(code(conversor), "Conversor Sin Transacciones")
  }

  def code(l: List[Conversor]) = { (times: Int) =>
    for (i <- 1 to times) {
      for (c <- l) {
        c.millas = i
        c.convertir
      }
    }
  }

  def execute(code: Int => Unit, procces: String) {
    val times = new ArrayBuffer[Long]

    for (i <- 1 to 10) {
      val start = time
      for (i <- 0 to 40000) {
        code(25)
      }

      val t = (time - start)
      times += t
    }
    
    println(procces + " min: " + times.min + ", max: " + times.max + ", avg: " + times.sum / times.length )
  }

  def executeAndCommit(code: Int => Unit, procces: String) {
    execute({ i: Int =>
      ObjectTransactionManager.begin(this)
      code(i)
      ObjectTransactionManager.commit(this)
    }, procces)
  }

  def executeAndRollback(code: Int => Unit, procces: String) {
    execute({ i: Int =>
      ObjectTransactionManager.begin(this)
      code(i)
      ObjectTransactionManager.rollback(this)
    }, procces)
  }

}

object Conversor {
  def apply() = new Conversor
}
class Conversor {
  var millas: Double = 0
  var kilometros: Double = _

  def convertir() {
    this.kilometros = this.millas * 1.60934;
  }
}

@TransactionalAndObservable
case class ConversorTransaccional extends Conversor



