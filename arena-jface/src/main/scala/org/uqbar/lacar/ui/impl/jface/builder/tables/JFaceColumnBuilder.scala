package org.uqbar.lacar.ui.impl.jface.builder.tables

import java.util.ArrayList

import org.eclipse.jface.viewers.TableViewerColumn
import org.eclipse.swt.SWT
import org.uqbar.arena.widgets.tables.ColumnLayoutBuilder
import org.uqbar.arena.widgets.traits.WidgetWithAlignment
import org.uqbar.lacar.ui.impl.jface.builder.traits.Aesthetic
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableLayoutBuilder
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder
import org.uqbar.lacar.ui.model.BindingBuilder
import org.uqbar.lacar.ui.model.ColumnBuilder
import org.uqbar.lacar.ui.model.LabelProvider

class JFaceColumnBuilder[Row](table: JFaceTableBuilder[Row], var labelProviders: java.util.List[LabelProvider[Row]])
  extends AbstractWidgetBuilder
  with ColumnBuilder[Row]
  with WidgetWithAlignment
  with Aesthetic {

  var tableViewerColumn = new TableViewerColumn(table.viewer, SWT.NONE)
  var layoutBuilder: ColumnLayoutBuilder[JFaceTableLayoutBuilder] = new DefaultColumnLayoutBuilder()
  var bindings = new ArrayList[BindingBuilder]()

  override def setTitle(title: String) = {
    tableViewerColumn.getColumn.setText(title)
  }

  override def setWeight(weight: Int) = {
    layoutBuilder = new WeightColumnLayoutBuilder(weight)
  }

  override def setFixedSize(pixels: Int) {
    layoutBuilder = new FixedColumnLayoutBuilder(pixels)
  }

  override def pack() = {
    super.pack
    tableViewerColumn.getColumn.pack
  }
  
  def widget = tableViewerColumn.getViewer.getControl

  override def alignLeft() = tableViewerColumn.getColumn.setAlignment(SWT.LEFT)
  override def alignRight() = tableViewerColumn.getColumn.setAlignment(SWT.RIGHT)
  override def alignCenter() = tableViewerColumn.getColumn.setAlignment(SWT.CENTER)

}