package workshop3.assignments

abstract class ImperativeDinner {

	def getCharcoal: Charcoal
	def getLighterFluid: LighterFluid
	def getMeat: Meat
	def lightBBQ(c: Charcoal, lf: LighterFluid): Fire
	def seasonMeat(m: Meat): Steak
	def grill(s: Steak, f: Fire): Dinner

	// this method never returns null!!!
	def orderPizza: Dinner = "I ordered a pizza"

	def prepareDinner: Dinner = {
		val charcoal = getCharcoal
		val lighterFluid = getLighterFluid
		val meat = getMeat

		val hopefullyDinner =
			if (charcoal != null && lighterFluid != null && meat != null) {
				val bbq = lightBBQ(charcoal, lighterFluid)
				if (bbq != null) {
					val steak = seasonMeat(meat)
					if (steak != null) {
						grill(steak, bbq)
					}
					else null
				}
				else null
			}
			else null

		if (hopefullyDinner == null) orderPizza else hopefullyDinner
	}
}

abstract class HigherOrderDinner {

	// this method never returns null!!!
	def orderPizza: Dinner = "I ordered a pizza"

	def prepareDinner: Dinner = ???
}

abstract class DinnerWithForComprehension {

	// this method never returns null!!!
	def orderPizza: Dinner = "I ordered a pizza"

	def prepareDinner: Dinner = ???
}
