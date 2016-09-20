package workshop3.assignments.solutions

import workshop3.assignments._

trait DinnerIngredients {

  def getCharcoal: Option[Charcoal]
  def getLighterFluid: Option[LighterFluid]
  def getMeat: Option[Meat]
  def lightBBQ(c: Charcoal, lf: LighterFluid): Option[Fire]
  def seasonMeat(m: Meat): Option[Steak]
  def grill(s: Steak, f: Fire): Option[Dinner]
}

abstract class HigherOrderDinner extends DinnerIngredients {

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

abstract class DinnerWithForComprehension extends DinnerIngredients {

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

trait SuccessfulDinner extends DinnerIngredients {
  def getCharcoal = Option("charcoal")

  def getLighterFluid = Option("lighter fluid")

  def getMeat = Option("meat")

  def lightBBQ(c: Charcoal, lf: LighterFluid) = Option(s"$c and $lf")

  def seasonMeat(m: Meat) = Option(s"seasoned $m")

  def grill(s: Steak, f: Fire) = Option(s"grill $s on $f")
}

trait NoCharcoalDinner extends SuccessfulDinner {
  override def getCharcoal = Option.empty
}

trait NoMeatDinner extends SuccessfulDinner {
  override def getMeat = Option.empty
}

object DinnerSolution extends App {

  // select one of the variants below to see the result

  val dinner = new HigherOrderDinner with SuccessfulDinner
//  val dinner = new HigherOrderDinner with NoCharcoalDinner
//  val dinner = new HigherOrderDinner with NoMeatDinner

//  val dinner = new DinnerWithForComprehension with SuccessfulDinner
//  val dinner = new DinnerWithForComprehension with NoCharcoalDinner
//  val dinner = new DinnerWithForComprehension with NoMeatDinner

  println(dinner.prepareDinner)
}
