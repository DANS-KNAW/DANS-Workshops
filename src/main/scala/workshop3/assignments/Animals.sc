type Animal = String
type Sound = String
type Food = String

class Animals(animalSays: Map[Animal, Sound], asksForFood: Map[Sound, Food]) {

  // returns an Option of the sound an animal makes
  def whatSays(animal: Animal): Option[Sound] = ???

  // returns an Option of the sound (in CAPITAL) an animal makes
  def WHAT_SAYS(animal: Animal): Option[Sound] = ???

  // returns an Option of the food an animal eats
  def whatEats(animal: Animal): Option[Food] = ???

  // returns a List of "<animal> eats <food>" based on what each animal likes to eat.
  def whatEat(animals: List[Animal]): List[String] = ???

  // returns an Option of the food (in CAPITAL) an animal eats
  def WHAT_EATS(animal: Animal): Option[Food] = ???
}
