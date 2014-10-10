package org.uqbar.swt.widgets

import scala.collection.JavaConversions.mapAsScalaMap

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyleRange
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.widgets.Composite
import org.uqbar.arena.widgets.style.Style
import org.uqbar.ui.swt.utils.SWTUtils

class KeyWordText(parent: Composite, var configuration: java.util.Map[Array[String], Style]) extends StyledText(parent, SWT.MULTI | SWT.BORDER) {

  override def setText(text: String) {
    super.setText(text)
    paintStyle
  }

  def paintStyle() {
    var currentText = getText
    configuration.foreach {
    	case (keywords, style) => keywords foreach{ addStyleRange(currentText, style, _) }
    }
  }

  def addStyleRange(currentText: String, style: Style, keyword: String) {
    val regex = keyword.r.pattern.matcher(currentText)
    while (regex find()) {
      val stylerange = new StyleRange()
      stylerange.start = regex.start
      stylerange.length = regex.end - regex.start
      setConfigurationStyle(style, stylerange)
      setStyleRange(stylerange)
    }
  }

  def setConfigurationStyle(style: Style, stylerange: StyleRange) {
    if (style.backgound != null)  stylerange.background = SWTUtils.getSWTColor(getDisplay(), style.backgound);
    if (style.foreground != null) stylerange.foreground = SWTUtils.getSWTColor(getDisplay(), style.foreground);
    if (style.fontStyle != 0) stylerange.fontStyle = style.fontStyle;
    stylerange.underline = style.underline;
  }
}