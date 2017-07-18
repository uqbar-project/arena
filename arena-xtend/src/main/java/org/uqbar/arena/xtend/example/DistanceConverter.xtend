package org.uqbar.arena.xtend.example

import org.eclipse.xtend.lib.annotations.Accessors
import org.uqbar.commons.model.annotations.Observable

@Observable
@Accessors
class DistanceConverter {
	public static String MILES = "miles"
	public static String KILOMETERS = "kilometers"
	public static String CONVERT = "convert"
	
	Double miles = 0.0
	Double kilometers = 0.0 
	
	def convert() {
		kilometers = miles * 1.60934
	}
	
//	@Dependencies("miles")
//	def getConversionEnabled() {
//		this.miles > 100
//	}
	
}
