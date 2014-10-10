package org.uqbar.lacar.ui.impl.jface.builder.tables

import java.util.ArrayList
import scala.collection.JavaConversions.asScalaBuffer
import org.eclipse.jface.databinding.viewers.ViewersObservables
import org.eclipse.jface.viewers.TableLayout
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.RowData
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Table
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.builder.JFaceControlBuilder
import org.uqbar.lacar.ui.impl.jface.builder.traits.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableItemsBindingBuilder
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableLayoutBuilder
import org.uqbar.lacar.ui.model.LabelProvider
import org.uqbar.lacar.ui.model.TableBuilder
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.RowLayout

class JFaceTableBuilder[R](container: JFaceContainer, var itemType: Class[R])
  extends JFaceControlBuilder[Table](container)
  with TableBuilder[R] {

  var columns = new ArrayList[JFaceColumnBuilder[R]]
  var viewer = this.createTableViewer(container.getJFaceComposite)
  
  initialize(viewer.getTable)
  setNumberVisibleRows(3)

  def createTableViewer(jFaceComposite: Composite) = {
    val viewer = new TableViewer(new Composite(jFaceComposite, 0), SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER)
    viewer.getTable.setLinesVisible(true)
    viewer.getTable.setHeaderVisible(true)
    viewer.getTable.setLayout(new TableLayout())
    viewer
  }

  override def addColumn(labelProvider: java.util.List[LabelProvider[R]]) = {
    val column = new JFaceColumnBuilder[R](this, labelProvider)
    columns.add(column)
    column
  }

  override def observeValue = new JFaceBindingBuilder(this, ViewersObservables.observeSingleSelection(viewer))
  override def observeContents = new JFaceTableItemsBindingBuilder[R](this)

  override def getControlLayout = viewer getControl

  def setNumberVisibleRows(numberVisibleRows: Int) {
    val layout = new FillLayout
    
    viewer.getControl().getParent().setBackground(new Color(viewer.getControl().getDisplay(),0,0,0))
    
    viewer.getControl().getParent().setLayout(layout)
    viewer.getTable.setLayout(new JFaceTableLayoutBuilder(this).createLayout)    
    columns foreach (_ pack)
    
    
    val rd = new RowData()
    rd.height = viewer.getTable().getItemHeight() * (numberVisibleRows + 1) 
    viewer.getControl().getParent().setLayoutData(rd)
    
    var p = viewer.getControl().getParent()
    while(p != null){
      p.layout()
      p = p.getParent()
    }
    
  }

  override def pack() = {
    viewer.getTable.setLayout(new JFaceTableLayoutBuilder(this).createLayout)    
    columns foreach (_ pack)
    
    super.pack()
  }

}