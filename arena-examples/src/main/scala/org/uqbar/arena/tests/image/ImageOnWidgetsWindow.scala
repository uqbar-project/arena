package org.uqbar.arena.tests.image

import scala.collection.JavaConversions._

import org.uqbar.arena.graphics.Image
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.scala.ArenaScalaImplicits._
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.Selector
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.MainWindow
import org.uqbar.commons.utils.Observable
import org.uqbar.lacar.ui.impl.jface.builder.JFacePanelBuilder

import com.uqbar.commons.collections.Transformer


@Observable
class ShareStory(var on : SocialNetwork = Facebook) {
  var status : String = _
  def getSocialNetworks() = { List(Facebook, Twitter, GooglePlus) }
}

@Observable
class SocialNetwork {
  override def toString = getClass().getSimpleName.replaceAll("\\$", "")
}
object Facebook extends SocialNetwork
object Twitter extends SocialNetwork
object GooglePlus extends SocialNetwork

object ImageOnWidgetsWindow extends MainWindow(new ShareStory()) with App {
  startApplication()

  override def createContents(p:Panel) = {
    p.setLayout(new VerticalLayout())
    val statusPanel = new Panel(p).setLayout(new HorizontalLayout)
    	new Label(statusPanel).setText("Status:")
    	
    	val textbox = new TextBox(statusPanel)
    	textbox.bindValueTo { s: ShareStory => s.status }
    
    val selectNetworkPanel = new Panel(p).setLayout(new HorizontalLayout)
//    	createImageOnLabel(selectNetworkPanel)
    	val s = new Selector(selectNetworkPanel)
    		s.bindItemsToProperty("socialNetworks")
    		s.bindValueToProperty("on")
   		createImageOnButton(selectNetworkPanel)
   		
//    createImageOnButton(p)
//    new Button(p).setCaption("Share")
//    new Link(p).setCaption("<a>Share</a>").onClick(() => println("Sharing!"))
  }
  
  private def createImageOnButton(p: Panel) = {
    val image = new Button(p)
    image setCaption("")
    image bindImageToProperty("on", { (s:SocialNetwork) => s match {
          case Facebook => new Image("facebook.png")
          case GooglePlus => new Image("googleplus.png")
          case Twitter => new Image("twitter.png")
    	}
      }
    )
  }
  
  private def createImageOnLabel(p: Panel) = {
    val image = new Label(p)
    
    image.bindImageToProperty("on", { (s:SocialNetwork) => s match {
          case Facebook => new Image("facebook.png")
          case GooglePlus => new Image("googleplus.png")
          case Twitter => new Image("twitter.png")
    	}
      }
    )
  }
  
}