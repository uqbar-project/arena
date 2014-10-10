package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Layout
import org.eclipse.swt.SWT

/**
 * trait for builders that can organize inner components through layouts.
 */
trait Layoutable {

  def setHorizontalLayout() = {
    val layout = new RowLayout(SWT.HORIZONTAL)
    layout.pack = true
    layout.wrap = true
    setLayout(layout)

  }

  def setLayoutInColumns(columnCount: Int) = setLayout(new GridLayout(columnCount, false))

  def setVerticalLayout() = {
    val layout = new RowLayout(SWT.VERTICAL)
    layout.fill = true
    layout.pack = true
    layout.wrap = true
    setLayout(layout)
  }

  protected def setLayout(layout: Layout): Unit

}