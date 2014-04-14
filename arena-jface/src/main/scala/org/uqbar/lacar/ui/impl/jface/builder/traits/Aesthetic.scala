package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.ui.swt.utils.SWTUtils
import org.eclipse.swt.widgets.Control
import java.awt.Color
import org.eclipse.swt.graphics.Font
import org.uqbar.lacar.ui.model.builder.traits.StyledWidgetBuilder

/**
 * El nombre es malisimo, no se me ocurrio otro.
 * Implementa metodos para especificar detalles esteticos
 * como colores de fondo, tamaño de letra, etc.
 */
//TODO: ver donde mas se puede usar esto ademas de las columnas.
trait Aesthetic extends StyledWidgetBuilder {
  // requirement
  def widget: Control

  override def setForeground(color: Color) {
    val swtColor = SWTUtils.getSWTColor(widget getDisplay, color)
    widget setForeground (swtColor)
  }

  override def setBackground(color: Color) {
    val swtColor = SWTUtils.getSWTColor(widget getDisplay, color)
    widget setBackground (swtColor)
  }

  override def setFontSize(size: Int) {
    val fontData = widget.getFont.getFontData
    fontData.foreach(f => f.setHeight(size))

    widget setFont (new Font(widget getDisplay, fontData))
    widget getFont
  }

}