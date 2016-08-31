package workshop3.assignments.solutions

import workshop3.assignments.Postcard

object PostcardSolution {
  val cities = List(
    "San Francisco", "Los Angeles",
    "Las Vegas", "San Diego",
    "Dallas", "Houston",
    "Chicago", "New York City",
    "Philadelphia")
  val relatives = List("Grandma", "Grandpa", "Aunt Dottie", "Dad")
  val travellers = List("Alice", "Bob")

  def sendPostcardsFunctional: List[Postcard] = {
    travellers.map(traveller => s"$traveller (your favorite)")
      .flatMap(sender => relatives
        .filter(relative => relative.startsWith("G"))
        .flatMap(relative => cities
          .map(theCity => Postcard(s"Dear $relative, Wish you were here in $theCity! Love, $sender"))
        ))
  }

  def sendPostcardsForComprehension: List[Postcard] = {
    for {
      traveller <- travellers
      sender = s"$traveller (your favorite)"
      relative <- relatives
      if relative.startsWith("G")
      theCity <- cities
    } yield Postcard(s"Dear $relative, Wish you were here in $theCity! Love, $sender")
  }
}
