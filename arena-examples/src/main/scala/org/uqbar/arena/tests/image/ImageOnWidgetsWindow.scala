package org.uqbar.arena.tests.image

import org.uqbar.arena.windows.MainWindow
import org.uqbar.commons.utils.Observable
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.Selector
import org.uqbar.arena.widgets.Button
import com.uqbar.commons.collections.Transformer
import org.uqbar.arena.graphics.Image
import org.uqbar.arena.scala.ArenaScalaImplicits._
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Link

@Observable
class ShareStory(var on : SocialNetwork = Facebook) {
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
    val s = new Selector(p)
    s.bindItemsToProperty("socialNetworks")
    s.bindValueToProperty("on")
    createImageOnButton(p)
    createImageOnLabel(p)
    
//    image.bindImageToProperty("on", new Transformer[SocialNetwork, Image] {
//      override def transform(s: SocialNetwork) = {
//        s match {
//          case Facebook => new Image("facebook.png")
//          case GooglePlus => new Image("googleplus.png")
//          case Twitter => new Image("twitter.png")
//        }
//      }
//    })
    new Button(p).setCaption("Share")
    new Link(p).setCaption("<a>Share</a>").onClick(() => println("Sharing!"))
  }
  
  private def createImageOnButton(p: Panel) = {
    val image = new Button(p)
    image.setCaption("")
    image.bindImageToProperty("on", { (s:SocialNetwork) => s match {
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