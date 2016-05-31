/*
  - private fields `i` and `s`
  - a constructor with arguments `i` and `s`
  - NO getters/setters
  - inherits standard `equals`, `hashCode` and `toString` methods from `AnyRef` (Scala's equivalent of `java.lang.Object`)
 */
class Foo(i: Int, s: String)

/*
  - public fields `i` and `s` (because of `val`/`var` keywords)
  - a constructor with arguments `i` and `s`
  - a getter for `i` (because it is a value)
  - a getter and setter for `s` (because it is a variable)
  - inherits standard `equals`, `hashCode` and `toString` methods from `AnyRef`
 */
class Bar(val i: Int, var s: String)

/*
  - Same as `Bar`
  - with default value for `s`
 */
class Foobar(val i: Int, var s: String = "")

/*
  - private fields `i` and `s`
  - public fields `isEven` and `stringLength`
  - a constructor with arguments `i` and `s`
  - a getter for `isEven` and `stringLength`
  - inherits standard `equals`, `hashCode` and `toString` methods from `AnyRef`
  - NOTICE: apart from the `isEven` and `stringLength`, `Baz` is equivalent to `Foo`! `private val` is the same as no keywords here.
 */
class Baz(private val i: Int, private val s: String) {
  val isEven = i % 2 == 0
  val stringLength = s.length
}

/*
  - same as `Bar`
  - companion object with `apply` methods (with this no `new` keyword is required anymore);
    second `apply` uses a default value for `s`
 */
class Qux(val i: Int, var s: String)
object Qux {
  def apply(i: Int, s: String): Qux = new Qux(i, s)
  def apply(i: Int): Qux = new Qux(i, "")
}

/*
  - same as Qux
  - with default parameter for `s` as an alternative to the second `apply`
 */
class Quux(val i: Int, var s: String)
object Quux {
  def apply(i: Int, s: String = ""): Quux = new Quux(i, s)
}

val foo = new Foo(1, "abc")
// can't access the fields of foo

val bar = new Bar(2, "def")
bar.i
bar.s
bar.s = "abc"
bar.s

val foobar = new Foobar(2)
foobar.i
foobar.s
foobar.s = "abc"
foobar.s

val baz = new Baz(3, "ghi")
// can't access the constructor fields
baz.isEven
baz.stringLength

val qux1 = Qux(4, "jkl")
// NOTE: you don't need to write `apply`!
qux1.s
val qux2 = Qux(5)
qux2.s

val quux1 = Quux(6, "mno")
quux1.s
val quux2 = Quux(7)
quux2.s
