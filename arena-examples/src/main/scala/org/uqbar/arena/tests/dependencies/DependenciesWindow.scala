package org.uqbar.arena.tests.dependencies

import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.MainWindow
import org.uqbar.commons.utils.Observable



@Observable
class Perro (var name:String ="")

@Observable
class Parent (var apellido:String ="", var perro:Perro = new Perro()){
  def nombrePerro() = perro.name
}

@Observable
class DependencyObject(var name:String = "", var parent:Parent=new Parent()){
  var _lastName = ""
  
  def getFullName() = name + lastName
  def description() = "Persona: " + getFullName()
  
  def lastName():String = parent.apellido  
  def nombrePerro():String = parent.nombrePerro 
}

object DependenciesWindow extends MainWindow(new DependencyObject()) with App {
  startApplication()
  
  override def createContents(p:Panel) = {
    p.setLayout(new VerticalLayout())
    new TextBox(p).bindValueToProperty("name")
    new TextBox(p).bindValueToProperty("parent.apellido")
    new TextBox(p).bindValueToProperty("parent.perro.name")
    new Label(p).bindValueToProperty("fullName")
    new Label(p).bindValueToProperty("description")
    new Label(p).bindValueToProperty("nombrePerro")
  }
}