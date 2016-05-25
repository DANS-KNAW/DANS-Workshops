class Car(year: Int, km: Int)

val car1 = new Car(1992, 50)


class Car2(val year: Int, val km: Int)
val car2 = new Car2(1992, 50)
car2.year
car2.km

class Car3(val year: Int, var km: Int)
val car3 = new Car3(1992, 0)
car3.year
car3.km
car3.km = 50
car3.km

class Car4(val year: Int, var km: Int = 0)
val car4 = new Car4(2015)

class Car5(val year: Int, var km: Int = 0) {
  def drive(distance: Int): Unit = {
    km += distance
  }
}
val car5 = new Car5(2016)
car5.km
car5.drive(20)
car5.km
car5 drive -20
car5.km
