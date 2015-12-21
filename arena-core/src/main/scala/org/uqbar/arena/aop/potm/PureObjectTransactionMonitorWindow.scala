package org.uqbar.arena.aop.potm

import org.uqbar.arena.scala.ArenaScalaImplicits._
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.widgets.tree.Tree
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.ErrorsPanel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.aop.transaction.ObjectTransactionImpl
import org.uqbar.arena.layout.VerticalLayout

class PureObjectTransactionMonitorWindow(parent: WindowOwner, model: MonitorApplicationModel)
  extends Dialog[MonitorApplicationModel](parent, model) {

  var ot: ObjectTransactionImpl = _
  
  override def createErrorsPanel(mainPanel:Panel):ErrorsPanel = {
	setTaskDescription("Open Transactions");
	super.createErrorsPanel(mainPanel)
  }

  override def createFormPanel(mainPanel: Panel) = {
    setTitle("Transaction Monitor")
    mainPanel.setLayout(new VerticalLayout)
    ot = getModelObject.getParent.getObjectTransaction

    createTree(mainPanel)
    var panel = new Panel(mainPanel)
    panel.setLayout(new VerticalLayout)

    createList(panel)
    createTable(panel)
  }

  def createTree(panel: Panel) {
    var tree = new Tree(panel)
    tree.bindContentsToProperty("parent", "children")
    tree.bindNodeToProperty("id")
	tree.bindValueToProperty("transaction")
    tree.setHeight(100)
    tree.setWidth(700)

  }

  def createTable(mainPanel: Panel) {
    var table = new Table[Entry](mainPanel, classOf[Entry])
    table bindItemsToProperty("tableResult")
    table setHeight(100)
    table setWidth(700)

    describeResultsGrid(table)
  }

  def describeResultsGrid(table: Table[Entry]) {
    new Column[Entry](table)
      .setTitle("property")
      .setFixedSize(200)
      .bindContentsToProperty("key")

    new Column[Entry](table)
      .setTitle("value")
      .setFixedSize(200)
      .bindContentsToProperty("value");

    new Column[Entry](table)
      .setTitle("original value")
      .setFixedSize(200)
      .bindContentsToProperty("fieldValue")
  }

  def createList(panel: Panel) = {
    var list = new org.uqbar.arena.widgets.List(panel)
    list bindItemsToProperty("listResult")
    list bindValueToProperty("transactionalObject")
    list setWidth(500)
    list setHeight(100)
  }

  override def addActions(actionsPanel: Panel) = {
    var close = new Button(actionsPanel).setCaption("Close")
//    close.setFontSize(25)//.setWidth(100).setHeight(50)
    close.onClick(() => this.close())
  }
}