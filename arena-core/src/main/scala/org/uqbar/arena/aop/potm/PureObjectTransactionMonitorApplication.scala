package org.uqbar.arena.aop.potm

import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class PureObjectTransactionMonitorApplication(var model:MonitorApplicationModel) extends Application {
  
  protected  def createMainWindow():Window[MonitorApplicationModel] = {
	  return new PureObjectTransactionMonitorWindow(this, model) 
  }
  
}