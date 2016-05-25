// a friend can listen to you
trait Friend {
  // to use the name in listen(), you need to declare it here
  // and give a value for it in each implementation
  // NOTE: we cannot use an abstract class for this; we will get to this later!
  val name: String

  def listen() = s"$name: I am listening"
}

// a human can be a friend - extending from a trait
case class Human(name: String) extends Friend

// declaration for any animal
class Animal(val name: String)

// a dog is an animal - extending from a class
// notice the override here! try to think of why we need it here...
case class Dog(override val name: String) extends Animal(name)

// a Human, 'Alice', can listen
val alice = new Human("Alice")
alice.listen()

// a Dog, "Maurice", cannot listen because neither Dog nor its super class have a method `listen()`
val maurice = new Dog("Maurice")
//maurice.listen()
