package org.uqbar.arena.tests.nestedColums

import org.uqbar.commons.utils.TransactionalAndObservable
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

@TransactionalAndObservable
case class Estudent(var name: String, var courses: java.util.List[Course], var department: Department, var status: EstudenStatus.Status)

object EstudenStatus extends Enumeration {
  type Status = Value
  val REGULAR, FREE, EXPELLED = Value
}

@TransactionalAndObservable
case class University(var name: String, var students: java.util.List[Estudent], var departments: java.util.List[Department], var courses: java.util.List[Course]) {
  var currentEstudent: Estudent = _ //solo para los test
}

object University {
  var courses = List(Course("Obj3"), Course("UI"), Course("Intro"), Course("Analisis"), Course("Economia"))
  var departments = List(Department("CyT"), Department("Sociales"), Department("Economia"))
  
  var students = List(
    Estudent("Pepe", courses, departments(0), EstudenStatus.REGULAR),
    Estudent("Marianno", courses, departments(1), EstudenStatus.FREE),
    Estudent("Marcos", courses, departments(1), EstudenStatus.EXPELLED),
    Estudent("Lucia", courses, departments(1), EstudenStatus.FREE),
    Estudent("Camila", courses, departments(2), EstudenStatus.EXPELLED),
    Estudent("Mirian", courses, departments(0), EstudenStatus.REGULAR))
    
  def university = University("UNQ", students, departments, courses)

}
@TransactionalAndObservable
case class Department(var name: String)
@TransactionalAndObservable
case class Course(var name: String)