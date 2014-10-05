package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.uqbar.arena.widgets.traits.Sizeable
import org.eclipse.swt.SWT
import org.uqbar.lacar.ui.model.WidgetBuilder
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Widget
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.layout.RowData

/**
 * Trait impl for Sizeable for swt components.
 * Implements the Stackable Trait Pattern
 * 
 * @author jfernandes
 */
trait JFaceSizeable extends Sizeable with WidgetBuilder {
  var width = SWT.DEFAULT
  var height = SWT.DEFAULT
  
  // requirement
  def widget : Control
  
  abstract override def pack() {
	  configureLayoutData
    super.pack
  }
  
  def configureLayoutData() {
    if (widget.getParent.getLayout.isInstanceOf[GridLayout]) {
      val layoutData = new GridData(GridData.FILL)
      layoutData.grabExcessHorizontalSpace = true
      layoutData.widthHint = width
      layoutData.heightHint = height
      widget.setLayoutData(layoutData)
    }

    if (widget.getParent.getLayout.isInstanceOf[RowLayout]) {
      widget.setLayoutData(new RowData(width, height))
    }
  }

  override def setWidth(preferredSize: Int) { width = preferredSize }
  override def setHeight(preferredSize: Int) { height = preferredSize }

}