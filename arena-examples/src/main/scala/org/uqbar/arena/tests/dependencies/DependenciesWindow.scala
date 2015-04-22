package org.uqbar.arena.tests.dependencies

import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.MainWindow
import org.uqbar.commons.utils.Observable
import org.uqbar.arena.layout.ColumnLayout

@Observable
class Direction(var address: String = "")

@Observable
class Job(var name: String = "", var direction: Direction = new Direction()) {
  def sign() = name + ", " + direction.address
}

@Observable
class Employee(var name: String = "", var lastName: String = "", var job: Job = new Job()) {

  def getFullName() = name + lastName
  def description() = "Persona: " + getFullName()
  def jobSign() = name + ", " + job.sign
}

object DependenciesWindow extends MainWindow(new Employee()) with App {
  startApplication()

  override def createContents(parent: Panel) = {
    val panel = new Panel(parent);
    panel.setLayout(new ColumnLayout(2))

    textWithLabel("Employee name", "name", panel)
    textWithLabel("Employee last name", "lastName", panel)
    textWithLabel("Job name", "job.name", panel)
    textWithLabel("Job direction", "job.direction.address", panel)

    labelWithLabel("Employee full name", "fullName", panel)
    labelWithLabel("Employee description", "description", panel)
    labelWithLabel("Employee job sign", "jobSign", panel)

  }

  def textWithLabel(label: String, property: String, parent: Panel) {
    new Label(parent).setText(label)
    new TextBox(parent)
    	.setWidth(100)
    	.bindValueToProperty(property)
  }
  def labelWithLabel(label: String, property: String, parent: Panel) {
    new Label(parent).setText(label)
    new Label(parent)
    	.setWidth(100)
    	.bindValueToProperty(property)
  }
}