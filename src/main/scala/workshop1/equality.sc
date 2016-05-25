/*
See also http://stackoverflow.com/questions/7681161/whats-the-difference-between-and-equals-in-scala
         http://daily-scala.blogspot.nl/2009/08/equals.html
 */

// a `Person` class
class Person(val name: String, var age: Int)

val p1 = new Person("Richard", 23)
val p2 = new Person("Richard", 23)

// `equals` is Java's `Object.equals`
p1.equals(p2)
p1.equals(p1)

// `==` is (almost) the same as `equals`
// it is NOT reference compare, as it is in Java
p1 == p2
p1 == p1

// `eq` is Scala's reference compare
p1.eq(p2)
p1.eq(p1)

// `==` adds proper null treatment to `equals`
//null.equals(p1) // throws exception
null == p1
null.eq(p1)

p1.equals(null)
p1 == null
p1.eq(null)

class Person2(val name: String, var age: Int) {
	override def equals(other: Any): Boolean = {
		other match {
			case that: Person2 => name == that.name && age == that.age
			case _ => false
		}
	}
}

val p3 = new Person2("Richard", 23)
val p4 = new Person2("Richard", 23)

p3.equals(p4)
p3.equals(p3)

p3 == p4
p3 == p3

p3.eq(p4)
p3.eq(p3)

//null.equals(p4)
null == p4
null.eq(p4)

p3.equals(null)
p3 == null
p3.eq(null)
