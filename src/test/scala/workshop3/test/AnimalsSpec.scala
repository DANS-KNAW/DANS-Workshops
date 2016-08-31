package workshop3.test

import org.scalatest.{FlatSpec, Matchers}
import workshop3.assignments.Animals

class AnimalsSpec extends FlatSpec with Matchers {

	val animalSays = Map("Dog" -> "Woof", "Cat" -> "Meow", "Cow" -> "Mooh")
	val asksForFood = Map("Woof" -> "Meat", "Meow" -> "Fish", "Mooh" -> "Grass")

	val animals = new Animals(animalSays, asksForFood)

	animalSays.foreach { case (animal, sound) =>
			"whatSays" should s"return Option($sound) when the input is $animal" in {
				animals.whatSays(animal) shouldBe Option(sound)
			}

			"WHAT_SAYS" should s"return a capatilized version of $sound with input $animal" in {
				animals.WHAT_SAYS(animal) shouldBe Option(sound.toUpperCase)
			}
	}

	"whatSays" should "return None if the animal is unknown" in {
		animalSays.keys.foreach(animal => animals.whatSays(s"me and $animal") shouldBe None)
	}

	"WHAT_SAYS" should "return None if the animal is unknown" in {
		animalSays.keys.foreach(animal => animals.WHAT_SAYS(s"me and $animal") shouldBe None)
	}

	"whatEats" should "return Meat on input Dog" in {
		animals.whatEats("Dog") shouldBe Some("Meat")
	}

	it should "return Fish on input Cat" in {
		animals.whatEats("Cat") shouldBe Some("Fish")
	}

	it should "return Grass on input Cow" in {
		animals.whatEats("Cow") shouldBe Some("Grass")
	}

	it should "return None if the animal is unknown" in {
		animalSays.keys.foreach(animal => animals.whatEats(s"me and $animal") shouldBe None)
	}
}
