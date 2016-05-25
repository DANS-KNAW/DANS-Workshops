trait Friend {
  val name: String

  def listen() = s"$name: I am listening"
}

case class Human(name: String) extends Friend

class Animal(val name: String)

// a Dog is now declared as both an Animal and a Friend
// notice that we use `with` here: only the first extension gets `extends`, the rest gets `with`
// you can extend from only 1 class, but from multiple interfaces! That is why Friend is a trait.
case class Dog(override val name: String) extends Animal(name) with Friend

val alice = new Human("Alice")
alice.listen()

// the Dog, "Maurice", can now listen because Dog now inherits from Friend
val maurice = new Dog("Maurice")
maurice.listen()
