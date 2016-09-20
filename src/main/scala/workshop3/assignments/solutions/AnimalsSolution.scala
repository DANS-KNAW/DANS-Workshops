package workshop3.assignments.solutions

import workshop3.assignments._

class AnimalsSolution(animalSays: Map[Animal, Sound], asksForFood: Map[Sound, Food]) {

  // returns an Option of the sound an animal makes
  def whatSays(animal: Animal): Option[Sound] = {
    animalSays.get(animal)
  }

  // returns an Option of the sound (in CAPITAL) an animal makes
  def WHAT_SAYS(animal: Animal): Option[Sound] = {
    whatSays(animal).map(_.toUpperCase)
  }

  // returns an Option of the food an animal eats
  def whatEats(animal: Animal): Option[Food] = {
    whatSays(animal).flatMap(asksForFood.get)
  }

  // returns a List of "<animal> eats <food>" based on what each animal likes to eat.
  def whatEat(animals: List[Animal]): List[String] = {
    animals.map(animal => whatEats(animal).map(food => s"$animal eats $food").getOrElse(s"I don't know what $animal eats!"))
  }

  // return a List of statements "<animal> says <sound> and eats <food>" based on the animals listed in the input.
  def whatSaysAndEats(animals: List[Animal]): List[String] = {
    animals.map(animal => {
      val text = for {
        sound <- whatSays(animal)
        food <- whatEats(animal)
      } yield s"$animal says '$sound' and eats $food"

      text.getOrElse(s"I don't know what $animal says or eats!")
    })
  }

  // returns an Option of the food (in CAPITAL) an animal eats
  def WHAT_EATS(animal: Animal): Option[Food] = {
    whatEats(animal).map(_.toUpperCase)
  }
}
