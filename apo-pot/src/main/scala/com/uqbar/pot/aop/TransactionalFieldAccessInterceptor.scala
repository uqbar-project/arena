package com.uqbar.pot.aop

import javassist.Modifier
import javassist.NotFoundException
import javassist.expr.FieldAccess
import org.apache.commons.lang.StringUtils
import com.uqbar.apo.FieldInterceptor
import com.uqbar.apo.parser.$originalAsigment
import com.uqbar.apo.parser.$originalReader

class TransactionFieldInterceptor extends FieldInterceptor {
  propertyKey = "TransactionFieldAccessInterceptor"

  write((statement, field) => {
    if (!Modifier.isTransient(field.getField().getModifiers())) {
      var newExpresion =
        """
		  $defaultField = ($fieldTypeName) $interceptor.fieldWrite($this, $S$fieldName$S, ($fieldTypeName)$argument1, ($fieldTypeName)$this.$fieldName);
		"""
      var reemplaze = $originalAsigment().name;
      statement.replace(reemplaze, newExpresion);
    }
  })

  read((statement, field) => {
    var reemplaze = $originalReader().name;
    var newExpresion = "$rtn ($fieldTypeName) $interceptor.fieldRead($this, $S$fieldName$S, $this.$fieldName);";
    statement.replace(reemplaze, newExpresion);
  })

}