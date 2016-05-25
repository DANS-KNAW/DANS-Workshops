class Car(val year: Int, var km: Int = 0) {
  def drive(distance: Int): Unit = {
    km += distance
  }

  override def equals(other: Any): Boolean = {
    other match {
      case other: Car => year == other.year && km == other.km
      case _ => false
    }
  }

  override def hashCode = ???
  override def toString = ???
}
object Car {
  def apply(year: Int, km: Int): Car = new Car(year, km)
  def unapply(car: Car): Option[(Int, Int)] = Some((car.year, car.km))
}

case class Car2(year: Int, var km: Int = 0) {
  def drive(distance: Int): Unit = {
    km += distance
  }

  override def toString = "foobar"
}

val car = Car2(2015, 5)

car match {
  case Car2(year, 0) => "fst"
  case Car2(_, x) => x
}
