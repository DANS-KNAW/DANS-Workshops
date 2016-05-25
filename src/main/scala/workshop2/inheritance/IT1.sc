// a human can listen to you
// BTW case classes in this example are just there for prettyprinting ;-)
case class Human(name: String) {
  def listen() = s"$name: I am listening"
}

val alice = new Human("Alice")
alice.listen()
