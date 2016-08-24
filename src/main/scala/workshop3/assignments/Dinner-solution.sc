type Charcoal = String
type LighterFluid = String
type Fire = String
type Meat = String
type Steak = String
type Dinner = String

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

  def getCharcoal: Option[Charcoal]
  def getLighterFluid: Option[LighterFluid]
  def getMeat: Option[Meat]
  def lightBBQ(c: Charcoal, lf: LighterFluid): Option[Fire]
  def seasonMeat(m: Meat): Option[Steak]
  def grill(s: Steak, f: Fire): Option[Dinner]

  // this method never returns null!!!
  def orderPizza: Dinner = "I ordered a pizza"

  def prepareDinner: Dinner = {
    getCharcoal
      .flatMap(charcoal => getLighterFluid
        .flatMap(lighterFluid => getMeat
          .flatMap(meat => lightBBQ(charcoal, lighterFluid)
            .flatMap(bbq => seasonMeat(meat)
              .flatMap(steak => grill(steak, bbq))))))
      .getOrElse(orderPizza)
  }
}

abstract class DinnerWithForComprehension {

  def getCharcoal: Option[Charcoal]
  def getLighterFluid: Option[LighterFluid]
  def getMeat: Option[Meat]
  def lightBBQ(c: Charcoal, lf: LighterFluid): Option[Fire]
  def seasonMeat(m: Meat): Option[Steak]
  def grill(s: Steak, f: Fire): Option[Dinner]

  // this method never returns null!!!
  def orderPizza: Dinner = "I ordered a pizza"

  def prepareDinner: Dinner = {
    val possibleDinner = for {
      charcoal <- getCharcoal
      lighterFluid <- getLighterFluid
      meat <- getMeat
      bbq <- lightBBQ(charcoal, lighterFluid)
      steak <- seasonMeat(meat)
      dinner <- grill(steak, bbq)
    } yield dinner

    possibleDinner.getOrElse(orderPizza)
  }
}
