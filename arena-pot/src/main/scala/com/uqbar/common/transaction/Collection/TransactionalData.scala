package com.uqbar.common.transaction.Collection

import org.uqbar.commons.utils.TransactionalAndObservable
import org.uqbar.commons.utils.When
import org.uqbar.commons.utils.Observable
import org.uqbar.commons.model.ObservableUtils
import org.uqbar.commons.utils.ReflectionUtils

@Observable
abstract class TransactionalData[D](owner:Any, fieldName:String) {

  When(this) change "data" fireChange fieldName of owner
  var data: D = _
  

  def setData(aData: D) {
    aData match{
      case null => data = defauldValue
      case td:TransactionalData[D] => data = td.data
      case data => this.data = data
    }
  }

  protected def updateData[T](action: (D) => T): T = {
    var act = action(data)
    ReflectionUtils.invokeSetter(owner, fieldName, this)
    ObservableUtils.firePropertyChanged(owner, fieldName, this)
    act
  }

  protected def defauldValue: D
  
  override def toString() = data.toString
}
