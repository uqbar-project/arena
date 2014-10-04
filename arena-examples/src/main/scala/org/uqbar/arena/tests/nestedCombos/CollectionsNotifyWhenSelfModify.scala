package org.uqbar.arena.tests.nestedCombos

import org.uqbar.arena.Application
import org.uqbar.arena.actions.MessageSend
import org.uqbar.arena.aop.windows.TransactionalDialog
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.utils.TransactionalAndObservable

class CollectionsNotifyWhenSelfModify(owner: WindowOwner) extends TransactionalDialog[Country](owner, new Country("wewer")) {

  override def createFormPanel(mainPanel: Panel) {
    new Button(mainPanel)
      .setCaption("Open Monitor")
      .onClick(new MessageSend(this, "openMonitor"));

    new Label(mainPanel).setText("Pais")
    new Label(mainPanel).bindValueToProperty("name")

    new TextBox(mainPanel).bindValueToProperty("provinceName")
    new Button(mainPanel).setCaption("add").onClick(new MessageSend(this.getModelObject(), "addProvince"));
    new Button(mainPanel).setCaption("remove").onClick(new MessageSend(this.getModelObject(), "removeProvince"));
    new Button(mainPanel).setCaption("save").onClick(new MessageSend(this, "accept"));

    val table = new Table[Province](mainPanel, classOf[Province]);
    table.setWidth(200);
    table.setHeight(200);
    table.bindItemsToProperty("provinces");
    table.bindSelectionToProperty("provinceSelected")

    new Column[Province](table).setTitle("Nombre").bindContentsToProperty("name")
  }

  override def addActions(actionsPanel: Panel) {

  }

  override def visibleMonitor() = true
}

object CollectionsNotifyWhenSelfModifyRunner extends Application with App {

  def createMainWindow(): Window[_] = {
    new CollectionsNotifyWhenSelfModify(this)
  }
  start()
}