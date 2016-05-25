class Car(val year: Int, val km: Int)

val car1 = new Car(2015, 20)
val car2 = new Car(2015, 20)

car1.equals(car2)
car1 == car2

"foo".equals("foo")
"foo" == "foo"

//null.equals(car2)
car2.equals(null)
null == car2
car2 == null

car1.eq(car2)
car1.eq(car1)

car1.ne(car2)
car1.ne(car1)

class Car2(val year: Int, val km: Int) {

  override def equals(other: Any): Boolean = {
    other match {
      case other: Car2 => year == other.year && km == other.km
      case _ => false
    }
  }
}

val car3 = new Car2(2015, 20)
val car4 = new Car2(2015, 20)

car3.equals(car4)
car3 == car4

car3 != car4
