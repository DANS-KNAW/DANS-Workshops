<!-- inspired by Scala for the Impatient -->
# Point
1. Define a class `Point` that describes an (x,y) coordinate.
2. Create a companion object such that you can create instances of `Point` without writing new.
3. Create a singleton object `Origin` that represents the (0,0) coordinate.
4. Check that two instances of `Origin` refer to the same object in memory.
5. Implement a function `distanceTo` in the `Point` class that calculates the distance between two `Point` instances. **Hint:** you need the [Pythagorean theorem](https://en.wikipedia.org/wiki/Pythagorean_theorem) as well as [Scala's math object](http://www.scala-lang.org/api/current/#scala.math.package).
6. Can you refactor `Point` to a case class? Which code can you get rid of? Can you still extend `Origin` from `Point` now that it is a case class?
