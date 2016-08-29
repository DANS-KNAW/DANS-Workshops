#Higher-order functions

Suppose you are asked to implement a function that counts the number of characters in a String (without using String.length, String.size). An imperative way of doing this (note: we are not doing functional programming yet!) would be to iterate over the String with a for-loop and incrementing an integer `count` in every cycle.

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

Once you're finished, you are also asked to implement a function that counts only the number of a's in a String. Copy-paste is your friend, so you copy the code above and add an extra if-statement:

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

Finally you are asked to implement a function that counts the number of b's **and** c's in a String. Again you can copy-paste the latest String and modify it a bit:

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

Looking back at this code makes you realize that this is not a good practice. A great way to refactor these functions in this case is to write a higher-order function.

A higher-order function is a function that takes as its argument another function. This is different from the functions above that just take 'normal' values as their arguments. Higher-order functions look as follows:

`def name(f: I => O): X`

where

* `name` is the higher-order function's name
* `f` is the name of the argument (note that 'f' denotes a function!)
* `I` is the input type of the inner-function
* `O` is the output type of the inner-function
* `X` is the return type of the higher-order function

Note that higher-order functions can also take '*normal*' values as their arguments. The 'higher-order' just signifies that one or more arguments are functions.

In the case of the counting examples, we could generalize the second and third function by using a higher-order function for the predicate in the if-statements. First we can write these predicates as '*normal*' functions:

```scala
def charIsA(c: Char): Boolean = {
  c == 'a'
}
def charIsBorC(c: Char): Boolean = {
  c == 'b' || c == 'c'
}
```

Note that both these functions have an input argument of type Char and an output of type Boolean. We write the type of these functions as `Char => Boolean` (pronounced "*`Char` to `Boolean`*").

We can now use this function type as an argument in the refactored counting-function:

```scala
def count(string: String, predicate: Char => Boolean) = {
  var count = 0
  for (c <- string) {
    if (predicate.apply(c)) // or 'if (predicate(c))' (you don't need to write 'apply'!)
      count += 1
  }
  count
}
```

We use this new `count` function by giving it the other functions as its arguments.

```scala
count("abcdef", charIsA)
count("abba", charIsA)
count("abcdef", charIsBorC)
count("abba", charIsBorC)
```

The first function (`numberOfChars`) can also be written using the new `count` function. For this we define a function that always returns true, no matter what character it is. Then we feed this function to 'count'.

```scala
def allChars(c: Char): Boolean = true
count("abcdef", allChars)
count("abba", allChars)
```

We do not always need to define functions like 'charIsA', 'charIsBorC' and 'allChars'. You can use an anonymous function (also known as *lambda expression*) as well. These look exactly like the type and implementation of the inner-functions we defined above:

```scala
(c: Char) => c == 'a'
(c: Char) => c == 'b' || c == 'c'
(c: Char) => true
```

Basically you can think of these anonymous functions as the same functions as the ones before, but without a name.

```scala
count("abcdef", (c: Char) => c == 'a')
count("abcdef", (c: Char) => c == 'b' || c == 'c')
count("abcdef", (c: Char) => true)
```

In this case the compiler already knows the type of `c` since we defined it in the type definition of `count`, so we do not need to write that either:

```scala
c => c == 'a'
c => c == 'b' || c == 'c'
c => true
```

Besides that, if the argument is never used, it does not require a name either, so it can be replaced by an underscore: `_ => true`.

On the same note: if an argument is only used once, often the `c => c` part can be replaced by an underscore as well: `_ == 'a'`

```scala
count("abcdef", _ == 'a')
count("abcdef", c => c == 'b' || c == 'c')
count("abcdef", _ => true)
```

Final note: the function we have defined is also present in the Scala API and does exactly the same!

```scala
"abcdef".count(_ == 'a')
"abcdef".count(c => c == 'b' || c == 'c')
"abcdef".count(_ => true) // which is equal to "abcdef".length
```
