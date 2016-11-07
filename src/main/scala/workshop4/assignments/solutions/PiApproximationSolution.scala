package workshop4.assignments.solutions

import rx.lang.scala.Observable
import workshop4.assignments.PiApproximation.Hits

import scala.util.Random

object PiApproximationSolution {

  /**
   * Convert a `Boolean` to an `Int`. If `true` then 1 else 0.
   *
   * @param b the boolean to be converted
   * @return the integer conversion
   */
  def booleanToInt(b: Boolean): Int = if (b) 1 else 0

  /**
   * Groups the number of successful hits with the total number of hits
   *
   * @param successes number of successful hits
   * @param total total number of hits
   */
  case class Hits(successes: Int, total: Int)
  object Hits {
    def empty: Hits = Hits(0, 0)
  }

  def random: Observable[Double] = {
    Observable.defer(Observable.just(Random.nextDouble())).repeat
  }

  def observableSequenceSplitIntoParts(): Unit = {

    val groupsOfTwo: Observable[Seq[Double]] = random.tumblingBuffer(2)

    val insideCircle: Observable[Boolean] = groupsOfTwo.map {
      case Seq(x, y) => math.sqrt(x * x + y * y) <= 1.0
    }

    val hits: Observable[Hits] = insideCircle.scan(Hits.empty) {
      case (Hits(successes, total), newHit) => Hits(successes + booleanToInt(newHit), total + 1)
    }.drop(1)

    val piApprox: Observable[Double] = hits.map { case Hits(successes, total) => successes * 4.0 / total }

    val oneMillionApproximations: Observable[Double] = piApprox.take(1000000)

    oneMillionApproximations.subscribe(println(_), _.printStackTrace(), () => println("done"))
  }

  def main(args: Array[String]): Unit = {

    random
      .tumblingBuffer(2) // take 2 random numbers
      .map { case Seq(x, y) => x * x + y * y <= 1 } // check whether x^2 + y^2 <= 1
      .scan(Hits.empty) {
        case (prev, newHit) => Hits(prev.successes + booleanToInt(newHit), prev.total + 1) // count the number of success hits and total hits
      }
      .drop(1) // drop the first
      .map(hits => (hits.successes * 4.0) / hits.total) // calculate pi
      .take(1000000) // use 1 million iterations
      .subscribe(println(_), _.printStackTrace(), () => println("done"))
  }
}
