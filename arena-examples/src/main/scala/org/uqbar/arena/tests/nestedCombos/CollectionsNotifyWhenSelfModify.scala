package org.uqbar.arena.tests.nestedCombos

import org.uqbar.arena.Application
import org.uqbar.arena.aop.windows.TransactionalDialog
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.Action

class CollectionsNotifyWhenSelfModify(owner: WindowOwner) extends TransactionalDialog[Country](owner, new Country("wewer")) {

  override def createFormPanel(mainPanel: Panel) {
    new Button(mainPanel)
      .setCaption("Open Monitor")
      .onClick(new Action() { def execute = openMonitor })

    new Label(mainPanel).setText("Pais")
    new Label(mainPanel).bindValueToProperty("name")

    new TextBox(mainPanel).bindValueToProperty("provinceName")
    new Button(mainPanel).setCaption("add").onClick(new Action() { def execute = getModelObject.addProvince() })
    new Button(mainPanel).setCaption("remove").onClick(new Action() { def execute = getModelObject().removeProvince() })
    new Button(mainPanel).setCaption("save").onClick(new Action() { def execute = accept })

    val table = new Table[Province](mainPanel, classOf[Province])
    table.setWidth(200)
    table.setHeight(200)
    table.bindItemsToProperty("provinces")
    table.bindSelectionToProperty("provinceSelected")

    new Column[Province](table).setTitle("Nombre").bindContentsToProperty("name")
  }

  override def addActions(actionsPanel: Panel) {

  }

}

object CollectionsNotifyWhenSelfModifyRunner extends Application with App {

  def createMainWindow(): Window[_] = {
    new CollectionsNotifyWhenSelfModify(this)
  }
  start()
}