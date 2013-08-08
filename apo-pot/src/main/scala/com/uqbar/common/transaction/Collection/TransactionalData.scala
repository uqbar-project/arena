package com.uqbar.common.transaction.Collection

abstract class TransactionalData[D] {

  var data: D = _

  def setData(aData: D) {
    if (aData == null) {
      this.data = defauldValue
    } else {
      this.data = aData;
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
