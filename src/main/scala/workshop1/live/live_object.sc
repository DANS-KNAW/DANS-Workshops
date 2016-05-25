object MathUtil {
  def max(x: Int, y: Int) = {
    if (x > y) x
    else y
  }
}

MathUtil.max(5, 3)

class Car(val year: Int, val km: Int)
object Car {
  def apply(year: Int, km: Int): Car = new Car(year, km)
  def unapply(car: Car): Option[(Int, Int)] = Some((car.year, car.km))
}

val car = Car(2015, 40)

car match {
  case Car(2015, km) => "car from 2015 - " + km
  case Car(year, _) => "car from other year, namely " + year
}

