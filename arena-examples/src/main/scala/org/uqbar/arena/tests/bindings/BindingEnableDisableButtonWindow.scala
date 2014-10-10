package org.uqbar.arena.tests.bindings

import org.uqbar.arena.windows.MainWindow
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.bindings.NotNullObservable
import org.uqbar.commons.utils.Observable
import org.uqbar.arena.widgets.TextBox
import org.uqbar.commons.model.UserException
import org.uqbar.arena.windows.ErrorsPanel
import org.uqbar.arena.windows.ErrorsPanel
import org.uqbar.arena.layout.ColumnLayout
import org.uqbar.arena.widgets.Label

/**
 * Checking if bindEnable and disableOnError (BUTTON) interfeer each other
 */
object BindingEnableDisableButtonWindow extends MainWindow[Friend](new Friend()) with App {
  startApplication()

  override def createContents(mainPanel: Panel) {
    new ErrorsPanel(mainPanel, "Name: any except error - Lucky: numbers except 13")

    var panel = new Panel(mainPanel)
    panel.setLayout(new ColumnLayout(2))

    new Label(panel).setText("Name:")
    new TextBox(panel).value().bindToProperty("name")

    new Label(panel).setText("Lucky:")
    new TextBox(panel).value().bindToProperty("luckyNumber")

    val bindEnableDisable = new Button(panel).setCaption("Enable -> Disable")
    bindEnableDisable.bindEnabledToProperty("lucky")
    bindEnableDisable.disableOnError()
  }
}

@Observable
class Friend {
  var name: String = null
  var luckyNumber: Integer = 24
  var lucky = true
  
  def setName(theString: String) = {
    if (theString.equals("error")) {
      throw new UserException("Error is allways wrong")
    }
    name = theString
  }

  def setLuckyNumber(luckyOne: Integer) = {
    luckyNumber = luckyOne
    lucky = luckyNumber != 13
  }

}