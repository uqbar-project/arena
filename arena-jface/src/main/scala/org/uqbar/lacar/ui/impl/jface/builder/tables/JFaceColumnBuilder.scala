package org.uqbar.lacar.ui.impl.jface.builder.tables

import org.uqbar.lacar.ui.model.ColumnBuilder
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder
import org.uqbar.lacar.ui.model.LabelProvider
import org.eclipse.jface.viewers.TableViewerColumn
import org.eclipse.swt.SWT
import java.util.ArrayList
import org.uqbar.lacar.ui.model.BindingBuilder
import org.uqbar.arena.widgets.tables.ColumnLayoutBuilder
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableLayoutBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.Aesthetic

class JFaceColumnBuilder[Row](table: JFaceTableBuilder[Row], var labelProviders: java.util.List[LabelProvider[Row]])
  extends AbstractWidgetBuilder
  with ColumnBuilder[Row]
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
  
  def getControl() = tableViewerColumn.getViewer.getControl

}