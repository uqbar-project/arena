package org.uqbar.arena.aop.potm

import org.uqbar.arena.windows.Window
import org.uqbar.arena.Application
import org.uqbar.commons.utils.Observable

class PureObjectTransactionMonitorApplication(var model:MonitorApplicationModel) extends Application {
  
  protected  def createMainWindow():Window[MonitorApplicationModel] = {
	  return new PureObjectTransactionMonitorWindow(this, model) 
  }
  
}