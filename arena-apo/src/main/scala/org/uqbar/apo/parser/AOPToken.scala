package org.uqbar.apo.parser
import javassist.CtPrimitiveType
import javassist.expr.FieldAccess
import org.uqbar.commons.utils.ReflectionUtils

abstract class Token[T]() {
  def apply(t: T): String
  def name = this.getClass().getSimpleName()
}


/**************************************************************************************/
/**************************************************************************************/
/***********************************CLASS**********************************************/
/**************************************************************************************/
/**************************************************************************************/


case class $this() extends Token[Any] {def apply(t: Any) = $this(t) }

case class $fieldTypeName() extends Token[FieldAccess] {def apply(field: FieldAccess) = $fieldTypeName(field)}

case class $argument() extends Token[Any] {def apply(t: Any) = $argument(t) }

case class $S() extends Token[Any] { def apply(t: Any) = $S(t) }

case class $rtn() extends Token[Any] { def apply(t: Any) = $rtn(t) }

case class $defaultValueAssignment() extends Token[Any] { def apply(t: Any) = $defaultValueAssignment(t) }

case class $originalAsigment() extends Token[FieldAccess] { def apply(t: FieldAccess) = $originalAsigment(t) }

case class $originalReader() extends Token[FieldAccess] { def apply(t: FieldAccess) = $originalReader(t) }

case class $defaultField() extends Token[FieldAccess] { def apply(fa: FieldAccess) = $defaultField(fa)}

case class $oldValue() extends Token[FieldAccess] {def apply(fa: FieldAccess) = $oldValue(fa)}

case class $fieldName() extends Token[{ def getFieldName(): String }] {
	def apply(field: { def getFieldName(): String }) = $fieldName(field)
}

case class $newValue() extends Token[Any] {def apply(a: Any) = $newValue(a)}

case class $Object() extends Token[Any] {	def apply(a: Any) = $Object(a) }

case class $coerceToObject() extends Token[FieldAccess] {def apply(f: FieldAccess) = $coerceToObject(f) }


/**************************************************************************************/
/**************************************************************************************/
/*********************************OBJECTS**********************************************/
/**************************************************************************************/
/**************************************************************************************/

object $this {def apply(t: Any) = "$0" }

object $fieldName {
	def apply(field: { def getFieldName(): String }) = field.getFieldName()
}

object $fieldTypeName {
	def apply(field: FieldAccess) = {
	  val f = field.getField() 
	  val c = Class.forName(f.getDeclaringClass().getName())
	  try{
		  ReflectionUtils.getField(c, f.getName()).getType().getName()
	  }catch{
	    case x:Exception => f.getType().getName()
	  } 
	}
}

object $argument {def apply(t: Any) = "$" }

object $S { def apply(t: Any) = "\"" }

object $rtn { def apply(t: Any) = "$_ =" }

object $defaultValueAssignment { def apply(t: Any) = $argument(t) + "1" }

object $originalAsigment { 
  def apply(t: FieldAccess) = $defaultField(t) + " = " + $defaultValueAssignment(t) + ";" 
 }

object $originalReader {
  def apply(t: FieldAccess) = $rtn(t) + " " + $defaultField(t) + ";"
}

object $defaultField {
  def apply(fieldAccess: FieldAccess) = $this(fieldAccess) + "." + $fieldName(fieldAccess);
}

object $oldValue {
  def apply(fa: FieldAccess) = $this(fa) + "." + $fieldName(fa)+ ";"
}

object $newValue {def apply(a: Any) = $argument(a) + "1" }

object $Object extends {def apply(a: Any) = classOf[Object].getName() }

object $coerceToObject {
	def apply(field: FieldAccess): String = {
			var typ = field.getField().getType();
			if (typ.isPrimitive()) "new " + typ.asInstanceOf[CtPrimitiveType].getWrapperName() else ""
	}
}
