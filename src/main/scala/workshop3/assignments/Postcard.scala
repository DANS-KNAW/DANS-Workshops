package workshop3.assignments

case class Postcard(msg: String)

object PostcardAssignment {

	val cities = List(
		"San Francisco", "Los Angeles",
		"Las Vegas", "San Diego",
		"Dallas", "Houston",
		"Chicago", "New York City",
		"Philadelphia")
	val relatives = List("Grandma", "Grandpa", "Aunt Dottie", "Dad")
	val travellers = List("Alice", "Bob")

	def sendPostcardsImperative: List[Postcard] = {
		var postcardList: List[Postcard] = List()

		for (h <- 0 until travellers.length) {
			val traveller = travellers(h)
			val sender = s"$traveller (your favorite)"
			for (i <- 0 until relatives.length) {
				val relative = relatives(i)
				if (relative.startsWith("G")) {
					for (j <- 0 until cities.length) {
						val theCity = cities(j)
						postcardList ::= Postcard(s"Dear $relative, Wish you were here in $theCity! Love, $sender")
					}
				}
			}
		}

		postcardList
	}

	def sendPostcardsFunctional: List[Postcard] = ???

	def sendPostcardsForComprehension: List[Postcard] = ???
}
