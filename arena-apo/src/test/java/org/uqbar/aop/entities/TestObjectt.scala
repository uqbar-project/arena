package org.uqbar.aop.entities
import scala.collection.mutable.Buffer

class TestObjectt {
  var name:String =_
  var fatherName:String =_
  
  def this(aName:String, aFatherName:String){
    this()
    name = aName
    fatherName = aFatherName
  }

  var listeners = Buffer[Listener]()

	def dispatch(event:String){
		listeners.foreach(_.listen(event))
	}
	
	def updateName(name:String)= this.name = name;
	
	def updateFatherName(name:String) = this.fatherName = name;
	
	def addListener(listener:Listener) = this.listeners.append(listener);
	
}
