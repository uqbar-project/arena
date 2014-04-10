package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.ui.swt.utils.SWTUtils
import org.eclipse.swt.widgets.Control
import java.awt.Color
import org.eclipse.swt.graphics.Font

/**
 * El nombre es malisimo, no se me ocurrio otro.
 * Implementa metodos para especificar detalles esteticos
 * como colores de fondo, tamaño de letra, etc.
 */
//TODO: ver donde mas se puede usar esto ademas de las columnas.
trait Aesthetic {
  // requirement
  def getControl() : Control
  
	def setForeground(color:Color) {
		val swtColor = SWTUtils.getSWTColor(getControl getDisplay, color)
		getControl setForeground(swtColor)
	}

	def setBackground(color: Color) {
		val swtColor = SWTUtils.getSWTColor(getControl getDisplay, color)
		getControl setBackground(swtColor)
	}

	def setFontSize(size:Int) {
		val fontData = getControl.getFont.getFontData
		fontData.foreach(f => f.setHeight(size))

		getControl setFont(new Font(getControl.getDisplay, fontData))
		getControl getFont
	}

}