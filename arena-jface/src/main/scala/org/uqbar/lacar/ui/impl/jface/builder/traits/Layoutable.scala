package org.uqbar.lacar.ui.impl.jface.builder.traits

import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Layout
import org.eclipse.swt.SWT

/**
 * trait for builders that can organize inner components through layouts.
 */
trait Layoutable {
  
  def setHorizontalLayout() = setLayout(new RowLayout(SWT.HORIZONTAL))
	def setLayoutInColumns(columnCount:Int) = setLayout(new GridLayout(columnCount, false))

	def setVerticalLayout() = {
		val layout = new RowLayout(SWT.VERTICAL)
		layout.fill = true
		setLayout(layout)
	}
  	
  	protected def setLayout(layout:Layout) :Unit 

}