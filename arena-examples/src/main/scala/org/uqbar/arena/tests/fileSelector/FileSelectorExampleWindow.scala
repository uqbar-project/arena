package org.uqbar.arena.tests.fileSelector

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.seqAsJavaList
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.scala.ArenaScalaImplicits.closureToAction
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.FileSelector
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.commons.utils.Observable
import org.uqbar.arena.windows.MainWindow
import org.uqbar.commons.utils.TransactionalAndObservable
import java.util.ArrayList
import org.uqbar.arena.widgets.List
import org.uqbar.arena.scala.ArenaScalaImplicits._
import org.uqbar.arena.bindings.PropertyAdapter

@TransactionalAndObservable class File(var path:String){
}
@TransactionalAndObservable class FileContainer(var files:java.util.List[File] = new ArrayList[File]()){
  def addFile(file:File) = files.append(file)
}

@Observable class FileContainerAppModel(var fileContainer:FileContainer=new FileContainer(), var currentFile:String=""){
  var fileSelected:File=_
  def addFile() = {
    fileContainer.addFile(new File(currentFile))
    currentFile = ""
  }
}


object FileSelectorExampleWindow extends MainWindow(new FileContainerAppModel()) with App {
  startApplication()

  override def createContents(p:Panel) = {
    p.setLayout(new VerticalLayout())
    
    val filePanel = new Panel(p)
    filePanel.setLayout(new HorizontalLayout())
    
    new Label(filePanel).setText("File:")
    new TextBox(filePanel)
    .setWidth(300)
    .bindValueToProperty("currentFile")
    
    new FileSelector(filePanel)
      .extensions("*.example") //opcional
      .setCaption("Open")
      .bindValueToProperty("currentFile")
      
    new Button(p)
    	.setCaption("Add")
    	.onClick(()=> this.getModelObject().addFile)
    	
    val list = new List(p)
    list.setHeight(100)
    list.bindItemsToProperty("fileContainer.files")
    	.setAdapter(new PropertyAdapter(classOf[File], "path"))
    list.bindValueToProperty("fileSelected")
	new TextBox(p).bindValueToProperty("fileSelected.path")

      
  }
  
}