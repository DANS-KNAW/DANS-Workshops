<!-- inspired by Scala for the Impatient -->
# Time
1. Create a class `Time` with read-only properties `hours` and `minutes` in [24-hour clock format](https://en.wikipedia.org/wiki/24-hour_clock).
2. Make sure that you can only create valid times. Instances like `Time(25, 69)` should cause an exception. Hint: have a look at [this blog post](http://daily-scala.blogspot.nl/2010/03/assert-require-assume.html) for a nice way of doing this.
3. Override the `toString` method that formats the time like `15:23`, `09:20` and `09:08`. Hint: use [this Stackoverflow question](http://stackoverflow.com/questions/8131291/how-to-convert-an-int-to-a-string-of-a-given-length-with-leading-zeros-to-align) for the formatting. 
4. Implement a method `isBefore(other: Time): Boolean` that checks whether `this` time comes before the `other` time.
5. Instead of `isBefore` you can also create a method `<` with the same type signature and the same specification. Experiment with this. Which of the two do you find more convenient to use?
6. Create a companion object with 2 `apply` methods:
    1. the first `apply` takes the same parameters as the class constructor and create a new `Time` instance with these parameters such that you don't need to write `new` all the time.
    2. the second `apply` takes a `String` with the same format as the `toString` implemented in 3 and creates an instance of `Time`. Test that `Time(s).toString == s` where `s` is a time as formatted in 3. 
7. Would it be beneficial to make `Time` a case class? What code could you remove with this? Would it provide features that `Time` currently don't have?
