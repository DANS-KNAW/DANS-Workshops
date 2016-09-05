Higher-order functions
======================

Suppose you are asked to implement a function that counts the number of characters in a string 
(without using `String.length` or `String.size`). An imperative way of doing this (note: we are not 
doing functional programming yet!) would be to iterate over the `String` with a for-loop and 
incrementing an integer `count` in every cycle.

```scala
def numberOfChars(string: String): Int = {
  var count = 0
  for (c <- string) {
    count += 1
  }
  count
}
numberOfChars("abcdef")
numberOfChars("abba")
```

Once you're finished, you are also asked to implement a function that counts only the number of a's 
in a string. Copy-paste is your friend, so you copy the code above and add an extra if-statement:

```scala
def numberOfA(string: String): Int = {
  var count = 0
  for (c <- string) {
    if (c == 'a')
      count += 1
  }
  count
}
numberOfA("abcdef")
numberOfA("abba")
```

Finally, you are asked to implement a function that counts the number of b's **and** c's in a string. 
Again you can copy-paste the latest implementation and modify it a bit:

```scala
def numberOfBandC(string: String): Int = {
  var count = 0
  for (c <- string) {
    if (c == 'b' || c == 'c')
      count += 1
  }
  count
}
numberOfBandC("abcdef")
numberOfBandC("abba")
```

Looking back at this code makes you realize that your are repeating yourself a lot (in violation of the [DRY]-principle). 
A great way to refactor these functions in this case is to write a **higher-order function**.

[DRY]: https://en.wikipedia.org/wiki/Don%27t_repeat_yourself

A higher-order function is a function that takes other functions as arguments or returns a function as result. This is different 
from the functions above that just take 'normal' values as their arguments. A higher-order function may look like this:

```scala
def name(f: I => O): X
```

where

* `name` is the higher-order function's name
* `f` is the name of the functional argument
* `I` is the input type of the functional argument
* `O` is the output type of the functional argument
* `X` is the return type of the higher-order function

Note that higher-order functions can also take *'normal'* values as their arguments. The term 'higher-order' 
just signifies that one or more arguments (or the return value) are functions.

In the case of the counting examples, we could generalize the second and third function by using a higher-order 
function for the predicate in the if-statements. First we can write these predicates as *'normal'* functions:

```scala
def charIsA(c: Char): Boolean = {
  c == 'a'
}
def charIsBorC(c: Char): Boolean = {
  c == 'b' || c == 'c'
}
```

Note that both these functions have an input argument of type `Char` and an output of type `Boolean`. We write 
the type of these functions as `Char => Boolean` (pronounced *"char to boolean"*).

We can now use this function type as an argument in the refactored counting-function:

```scala
def count(string: String, predicate: Char => Boolean): Int = {
  var count = 0
  for (c <- string) {
    if (predicate(c)) 
      count += 1
  }
  count
}
```

We use this new `count` function by giving it the other functions as its arguments.

```scala
// Can replace numberOfA
count("abcdef", charIsA)
count("abba", charIsA) 

// Can replace numberOfBandC
count("abcdef", charIsBorC) 
count("abba", charIsBorC) 
```

The first function (`numberOfChars`) can also be replaced by the new `count` function. For this we define 
a function that always returns true, no matter what character it is. Then we feed this function to `count`.

```scala
def allChars(c: Char): Boolean = true
count("abcdef", allChars)
count("abba", allChars)
```

We do not always need to define functions like `charIsA`, `charIsBorC` and `allChars`. You can use an anonymous 
function (also known as *lambda expression*) as well. These look exactly like the type and implementation of 
the functions we defined above:

```scala
(c: Char) => c == 'a'
(c: Char) => c == 'b' || c == 'c'
(c: Char) => true
```

Basically, you can think of these functions as the same functions as the ones before, but without 
a name (hence: "anonymous").

```scala
count("abcdef", (c: Char) => c == 'a')
count("abcdef", (c: Char) => c == 'b' || c == 'c')
count("abcdef", (c: Char) => true)
```

In this case the compiler already knows the type of `c` since we defined it in the type definition of 
`count`, so we do not need to write that, either:

```scala
c => c == 'a'
c => c == 'b' || c == 'c'
c => true
```

Besides that, if the argument is never used, it does not require a name either, so it can be replaced 
by an underscore: 

```scala
_ => true
```

On the same note: if an argument is only used once, often the `c => c` part can be replaced by an 
underscore as well: 

```scala
_ == 'a'
```

```scala
count("abcdef", _ == 'a')
count("abcdef", c => c == 'b' || c == 'c')
count("abcdef", _ => true)
```

Final note: the function we have defined is also present [in the Scala API](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.StringOps@count(p:A=%3EBoolean):Int)
and does exactly the same!

```scala
"abcdef".count(_ == 'a')
"abcdef".count(c => c == 'b' || c == 'c')
"abcdef".count(_ => true) // which is equal to "abcdef".length
```
