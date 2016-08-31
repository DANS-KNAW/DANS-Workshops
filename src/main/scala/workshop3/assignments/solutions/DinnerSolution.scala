package workshop3.assignments.solutions

import workshop3.assignments._

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
