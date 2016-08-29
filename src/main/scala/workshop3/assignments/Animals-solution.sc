// translated from https://github.com/frode-carlsen/scala-workshop/blob/master/scala-workshop/src/main/scala/oppgave2/SultneDyr.scala

type Animal = String
type Sound = String
type Food = String

class Animals(animalSays: Map[Animal, Sound], asksForFood: Map[Sound, Food]) {

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

  // returns an Option of the food (in CAPITAL) an animal eats
  def WHAT_EATS(animal: Animal): Option[Food] = {
    whatEats(animal).map(_.toUpperCase)
  }
}

val animalSays = Map("Dog" -> "Woof", "Cat" -> "Meow", "Cow" -> "Mooh")
val asksForFood = Map("Woof" -> "Meat", "Meow" -> "Fish", "Mooh" -> "Grass")
val animals = new Animals(animalSays, asksForFood)

animals.whatSays("Dog")
animals.whatSays("Fish")

animals.WHAT_SAYS("Dog")
animals.WHAT_SAYS("Fish")

animals.whatEats("Dog")
animals.whatEats("Fish")

animals.whatEat("Dog" :: "Cow" :: "Fish" :: "Cat" :: Nil)

animals.WHAT_EATS("Dog")
animals.WHAT_EATS("Fish")
