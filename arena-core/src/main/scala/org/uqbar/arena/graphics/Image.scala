package org.uqbar.arena.graphics

/**
 * @author jfernandes
 */
class Image(var pathToFile: String) {
	var scale : (Int,Int) = _
	
	// just to be used from java
	def size(width:Int, height:Int) {
	  scale = (width, height)
	}
}