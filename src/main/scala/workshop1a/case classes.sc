/*
  - public fields `i` and `s`
  - a constructor with arguments `i` and `s`
  - a companion object with `apply` (and some other methods)
  - no need for `new` keyword (because of the previous)
  - pattern-match capability
  - a getter for `i` and `s`
  - overrides `equals`, `hashCode` and `toString` methods based on the constructor parameters
 */
case class Foo(i: Int, s: String)

/*
  - public fields `i` and `s`
  - a constructor with arguments `i` and `s`
  - a companion object with `apply` (and some other methods)
  - no need for `new` keyword (because of the previous)
  - pattern-match capability
  - a getter for `i` and `s`
  - a setter for `s`
  - overrides `equals`, `hashCode` and `toString` methods based on the constructor parameters
 */
case class Bar(i: Int, var s: String)

/*
  - private fields `i` and `s`
  - public fields `isEven` and `stringLength`
  - a constructor with arguments `i` and `s`
  - a companion object with `apply` (and some other methods)
  - no need for `new` keyword (because of the previous)
  - pattern-match capability
  - a getter for `isEven` and `stringLength`
  - overrides `equals`, `hashCode` and `toString` methods based on the constructor parameters
 */
case class Baz(private val i: Int, private val s: String) {
  val isEven = i % 2 == 0
  val stringLength = s.length
}

val foo = Foo(1, "abc")
foo.i
foo.s

val bar = Bar(2, "def")
bar.i
bar.s
bar.s = "abc"
bar.s

val baz = Baz(3, "ghi")
// can't access the constructor fields
baz.isEven
baz.stringLength
