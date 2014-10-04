package org.uqbar.arena.tests.tooltip

import org.uqbar.arena.tests.nestedCombos.Country
import org.uqbar.arena.windows.MainWindow
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.tests.nestedCombos.Country
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.scala.ArenaScalaImplicits._
import org.uqbar.arena.widgets.Control
import org.uqbar.arena.widgets.Selector
import org.uqbar.arena.widgets.Link


object TooltipTextWindow extends MainWindow(new Country("Argentina")) with App {
  startApplication()
  
  override def createContents(p:Panel) = {
    p.setLayout(new VerticalLayout())
    val textbox = new TextBox(p)
    textbox.bindValueToProperty("name")
    applyTooltip(textbox)
    applyTooltip(new Button(p).setCaption("Boton"))
    applyTooltip(new Link(p).setCaption("Link"))
    val selector = new Selector(p)
    selector.bindItemsToProperty("provinces")
    applyTooltip(selector)
  }
  
  def applyTooltip(control:Control) = control.tooltip() <=> getModelObject@@{ _.name }

}