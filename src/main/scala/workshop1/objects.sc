// objects can be used for static methods/functions
object Math {
	def min(a: Int, b: Int): Int = {
		if (a < b) a
		else b
	}
	// just as any def, you don't need to provide
	// the return type
	def max(a: Int, b: Int) = {
		if (a > b) a
		else b
	}
}

val min = Math.min(5, 3)

// objects can be used in combination with classes
// they're called 'companion objects'
class Car(val year: Int, var km: Int = 0) {
	def drive(dist: Int) = {
		km += dist
	}
}
object Car {
	def apply(year: Int, km: Int): Car = new Car(year, km)
	def unapply(car: Car): Option[(Int, Int)] = Some((car.year, car.km))
}

// with an apply method you don't need to write 'new' anymore
// and you don't need to write 'apply' as well!
val car = Car(2015, 20) // this is the same as Car.apply(2015, 20)
car.year
car.km

val patternMatch = car match {
	case Car(2015, km) => s"I'm from 2015 and I have driven $km km"
	case Car(year, km) => s"I'm from another year"
}
