trait Friend {
  val name: String

  def listen() = s"$name: I am listening"
}

case class Human(name: String) extends Friend

class Animal(val name: String)

case class Dog(override val name: String) extends Animal(name) with Friend

// a Cat is an animal BUT IS NOT A FRIEND
case class Cat(override val name: String) extends Animal(name)

val alice = new Human("Alice")
alice.listen()

val maurice = new Dog("Maurice")
maurice.listen()

// a Cat, "Octa", cannot listen because neither Cat nor its super class have a method `listen()`
val octa = new Cat("Octa")
//octa.listen()

// another Cat, "Tom", can however listen, because it has the Friend inheritance on its instance
val tom = new Cat("Tom") with Friend
tom.listen()
