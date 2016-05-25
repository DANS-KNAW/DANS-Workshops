// this is a lot of code for such a simple thing:
class Car(val year: Int, var km: Int = 0) {
	def drive(dist: Int) = {
		km += dist
	}

	override def equals(other: Any): Boolean = {
		other match {
			case Car(y, k) => year == y && km == k
			case _ => false
		}
	}

	// we don't care about the implementation here
	override def hashCode(): Int = year + km

	override def toString: String = s"Car($year, $km)"
}
object Car {
	def apply(year: Int, km: Int): Car = new Car(year, km)
	def unapply(car: Car): Option[(Int, Int)] = Some((car.year, car.km))
}

val car1 = Car(2016, 1500)
car1.drive(20)
car1

// introducing case classes!
// case classes automatically generate:
//   class, constructor, fields, getters, setter (for km)
//   equals, hashCode, toString, apply and unapply
case class Car2(year: Int, var km: Int = 0) {
	def drive(dist: Int) = {
		km += dist
	}
}

val car2 = Car2(2012)
car2.drive(20)
car2
car2.year

car2 match {
	case Car2(2015, 20) => s"car from 2015, driven 20 km"
	case Car2(year, _) => s"car from $year"
}

// an even better design:
// but that's a story for another time ;-)
case class Car3(year: Int, km: Int = 0) {
	def drive(dist: Int) = Car3(year, km + dist)
}

val car3 = Car3(2012)
car3.drive(20)
