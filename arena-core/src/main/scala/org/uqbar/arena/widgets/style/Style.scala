package org.uqbar.arena.widgets.style

import java.awt.Color

object Style{
	val ITALIC = 1 << 1
	val BOLD = 1 << 0
}

class Style {

	var backgound:Color  = _
	var foreground:Color  = _
	var fontStyle:Int =_
	var underline:Boolean = _
	
	def backgound(backgound:Color): Style = {
		this.backgound = backgound
		this
	}
	def foreground(foreground:Color): Style =  {
		this.foreground = foreground
		this
	}

	def fontStyle(fontStyle:Int): Style = {
		this.fontStyle = this.fontStyle | fontStyle
		this
	}

	def underline(underline:Boolean): Style = {
		this.underline = underline
		this
	}
}
