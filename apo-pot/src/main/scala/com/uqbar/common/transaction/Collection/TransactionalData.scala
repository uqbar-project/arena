package com.uqbar.common.transaction.Collection

import org.uqbar.commons.utils.TransactionalAndObservable
import org.uqbar.commons.utils.When
import org.uqbar.commons.utils.Observable

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
    val data2 = data
    data = null.asInstanceOf[D]
    var act = action(data2)
    data = data2;
    act
    
  }

  protected def defauldValue: D
}
