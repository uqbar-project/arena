package org.uqbar.arena.examples.conversor.xtend

import org.uqbar.commons.utils.Observable

@Observable
class DistanceConverter {
	public static String MILES = "miles"
	public static String KILOMETERS = "kilometers"
	public static String CONVERT = "convert"
	
	@Property Double miles
	@Property Double kilometers 
	
	def convert() {
		kilometers = miles * 1.60934
	}
}
