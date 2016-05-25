trait Friend {
  val name: String

  def listen() = s"I ($name) am listening"
}

case class Human(name: String) extends Friend

case class Animal(name: String)

case class Dog(override val name: String) extends Animal(name) with Friend

case class Cat(override val name: String) extends Animal(name)

// you can talk to a Friend
def talkTo(friend: Friend) = {
  friend.listen()
}

val alice = new Human("Alice")
val maurice = new Dog("Maurice")
val octa = new Cat("Octa")
val tom = new Cat("Tom") with Friend

// you can talk to Alice, Maurice and Tom because they are friends
talkTo(alice)
talkTo(maurice)
talkTo(tom)

// Octa isn't a Friend however, so you cannot talk to it
//talkTo(new Cat("Octa"))
