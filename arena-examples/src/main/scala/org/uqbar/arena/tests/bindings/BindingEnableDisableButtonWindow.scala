package org.uqbar.arena.tests.bindings

import org.uqbar.arena.windows.MainWindow
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.bindings.NotNullObservable
import org.uqbar.commons.utils.Observable
import org.uqbar.arena.widgets.TextBox
import org.uqbar.commons.model.UserException

/**
 * Checking if bindEnable and disableOnError (BUTTON) interfeer each other 
 */
object BindingEnableDisableButtonWindow extends MainWindow[Friend] (new Friend()) with App{
	startApplication()
	
	override def createContents(mainPanel:Panel) {
	  new TextBox(mainPanel).value().bindToProperty("name")
	  
	  new TextBox(mainPanel).value().bindToProperty("luckyNumber")
	  
	  val bindEnableDisable = new Button(mainPanel).setCaption("Enable -> Disable")
	  bindEnableDisable.bindEnabled(new NotNullObservable("name"))
	  bindEnableDisable.disableOnError()
	  
      val bindDisableEnable = new Button(mainPanel).setCaption("Disable -> Enable")
      bindDisableEnable.disableOnError()
	  bindDisableEnable.bindEnabled(new NotNullObservable("name"))
	}
  
}

@Observable
class Friend {  
  var name:String=null
  var luckyNumber:Integer=24
  
  def setName(theString:String)={
    if(theString.equals("error")){
      throw new UserException("Error is allways wrong")
    }
    name = theString
  }
  
  def setLuckyNumber(luckyOne:Integer) = {
    if(luckyOne == 13){
      throw new UserException("13 is never lucky")
    }
    luckyNumber = luckyOne
  }

}