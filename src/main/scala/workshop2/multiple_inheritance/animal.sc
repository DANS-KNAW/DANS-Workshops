// we define a class Animal and override its toString method
class Animal {
  override def toString = "Animal"
}

// an Animal can be furry, so the toString needs to be overridden again.
// NOTE: we extend a trait from a class here!
trait Furry extends Animal {
  override def toString = s"Furry -> ${super.toString}"
}

// an Animal can also have legs.
trait HasLegs extends Animal {
  override def toString = s"HasLegs -> ${super.toString}"
}

// some legged animals have four legs
trait FourLegged extends HasLegs {
  override def toString = s"FourLegged -> ${super.toString}"
}

// a Cat is a furry, four legged animal.
// NOTE: we have multiple inheritance here
//   - Cat extends from Animal
//   - Cat extends from Furry (which extends from Animal)
//   - Cat extends from FourLegged (which extends from HasLegs, which extends from Animal)
// hint: draw an inheritance diagram of this situation
// the question now is in which order these superclasses are applied
class Cat extends Animal with Furry with FourLegged {
  override def toString = s"Cat -> ${super.toString}"
}

// from `toString` we see that superclasses get applied from right to left
new Cat().toString
