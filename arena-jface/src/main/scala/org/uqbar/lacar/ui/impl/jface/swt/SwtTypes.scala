package org.uqbar.lacar.ui.impl.jface.swt

import org.eclipse.swt.widgets.Widget
import org.eclipse.swt.events.SelectionListener
import org.eclipse.swt.widgets.Control

/**
 * This object defines type aliases to solve the lack of polymorphism
 * in SWT.
 * There are many classes in SWT which have the same methods but they don't
 * override it or implement a common interface, rendering them difficult to use
 * polymorphically.
 * With scala's type aliases and structural type we can avoid that problem.
 * 
 * @author jfernandes
 */
object SwtTypes {
  
  type SelectionListening = Control { def addSelectionListener(l:SelectionListener) }

  type WithText = Control {
	  def getText():String
	  def setText(a:String)
  } 
  
  type WidgetWithImage = Widget { 
    def getImage() : org.eclipse.swt.graphics.Image 
  	def setImage(image:org.eclipse.swt.graphics.Image) : Unit
  }
  
}