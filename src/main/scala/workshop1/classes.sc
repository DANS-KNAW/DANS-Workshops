// this generates the class `Car`,
//                the `year` and `km` fields (private, final)
//            and the constructor
class Car(year: Int, km: Int)

val c = new Car(1992, 0)

// you cannot access the fields in `Car`
// c.year

// this generates the same as in `Car`,
// but also a (public) getter for `year`
class Car2(val year: Int, km: Int)

val c2 = new Car2(1992, 0)
c2.year

// this generates the same as in `Car2`
// but also a (public) getter and setter for `km`
class Car3(val year: Int, var km: Int)

val c3 = new Car3(1992, 0)
c3.year
c3.km
c3.km = 120
c3.km

// you can set a default value for any value or variable
class Car4(val year: Int, var km: Int = 0)

val c4New = new Car4(1992)
c4New.km

val c4SecondHand = new Car4(1992, 500000)
c4SecondHand.km

// Just like in Java you can add methods to a class
// that mutate variables and can use both variables and values
class Car5(val year: Int, var km: Int = 0) {
	def drive(dist: Int) = {
		km += dist
	}
}

val c5 = new Car5(1992)
c5.km

c5.drive(20)
c5.km

c5.drive(50)
c5.km
