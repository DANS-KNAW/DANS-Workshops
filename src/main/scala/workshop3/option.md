#Option

The `map`, `filter`, `foreach` and `fold` operators which we saw in the last section are not only found in `List`, but also in other data structures. In this section we will cover the `Option` type.

Consider the following function which either returns a `String` or a `null` if the input is invalid

```scala
def makeStringOfLength(n: Int): String = {
  if (n <= 0) null
  else {
    val x = "abcdef"
    x * (n / x.length) + x.take(n % x.length)
  }
}
```

To use this function we always need to check for a potential null pointer. Notice, however, that this is not specified in the type of this function, which is `Int => String`. Unless the function is well documented or you have access to the source code, you do not have any clue as to when (or if) it can possibly return a null.

```scala
val s = makeStringOfLength(8)
if (s != null) {
  println(s.reverse)
}
```

These null checks can make your code less readable and also looks ugly! Besides that, it is easy to forget them, which can easily introduces bugs that are hard to find. To avoid these null checks, Scala introduces the `Option` type. By wrapping a value in an `Option`, you indicate that it can either be a value or a `null`. `Option[String]` (pronounced as '*Option of String*') therefore declares to be either a value of type `String` or a `null`.

The `Option` type is an abstract class that has two children: `Some` and `None`. The former indicates the case where there is a value, whereas the latter signals a `null`. To create an `Option`, you can either use these subclasses directly or use the equivalent `Option.apply` and `Option.empty` that are defined in the `Option`'s companion object. Often the latter approach is recommended above the former!

```scala
Some(123) == Option(123)
None      == Option.empty
```

With this we can refactor the function above to return an `Option[String]` instead.

```scala
def makeStringOfLength2(n: Int): Option[String] = {
  if (n <= 0) Option.empty
  else {
    val x = "abcdef"
    Option(x * (n / x.length) + x.take(n % x.length))
  }
}
```

Operators like `map` and `filter` are implemented such that they only run when there is a value in the `Option`. For example, in the two lines below the first will execute the `x => x + 1`, which is adding `1` to the `Int` that is inside the `Option`, whereas the second will not execute the `x => x + 1` because it does not have a value. In the first line a new `Option` is created with the new value in it, whereas in the second line the same `None` is passed along.

```scala
Option(5).map(x => x + 1)
Option.empty[Int].map(x => x + 1)
```

All other higher order functions on `Option` work in the same way. Have a look at the source code yourself at https://github.com/scala/scala/blob/2.12.x/src/library/scala/Option.scala to see how this is all implemented.

The refactored function above can be used as before, but now using the higher-order functions that are defined on `Option`:

```scala
makeStringOfLength2(8).map(s => s.reverse).foreach(s => println(s))
```

Alternatively we can use pattern matching instead. Verify for yourself that this is actually the same as using the higher-order functions above and that these higher-order functions do the pattern matching for you!

```scala
makeStringOfLength2(8) match {
  case Some(string) => println(string.reverse)
  case None => // do nothing
}
```

Note that we can even write the original function using these higher-order functions alone.

Verify for your self why this is equivalent to the previous implementation.
* where did the if/else go?
* why doesn't the result (`x * (i / x.length) + x.take(i % x.length)`) need to be wrapped in `Option`?

```scala
def makeStringOfLength3(n: Int): Option[String] = {
  Option(n)
    .filter(i => i > 0)
    .map(i => {
      val x = "abcdef"
      x * (i / x.length) + x.take(i % x.length)
    })
}
```

Getting a value out of an `Option` is done using the `get` operator. However, if the `Option` is empty, the `get` operator can obviously not return any value and can only throw an exception! Therefore this operator is only supposed to be used in the case that you are **ABSOLUTELY CERTAIN** that the `Option` contains a value!

If you are not certain about this, but still want to get the value out of the `Option`, you can use the operator `getOrElse`. In this operator you give a default value which will be returned instead if the `Option` is empty.

```scala
Option(5).get             // returns 5
//Option.empty.get        // throws an exception
Option(5).getOrElse(2)    // returns 5
Option.empty.getOrElse(2) // returns 2
```
