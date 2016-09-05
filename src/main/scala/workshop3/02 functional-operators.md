Functional operators
====================

`foreach`
---------

Consider the following 'imperative' code that prints all values of a list:

```scala
val list = List(1, 2, 3, 4)
for (elem <- list) {
  println(elem)
}
```

We can also write this using a higher-order function (or operator) that is defined on List and use the 
[`println`](http://www.scala-lang.org/api/current/index.html#scala.Predef$@println(x:Any):Unit) 
function as its argument:

```scala
list.foreach(println)
```

[`foreach`](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List@foreach(f:A=%3EUnit):Unit)
will apply this function to every element of the list.


`map`
-----

The following 'imperative' code doubles all values of a list and collects the results in a new list:

```scala
var result: List[Int] = Nil
for (elem <- list) {
  val doubled = elem * 2
  result = result :+ doubled
}
result
```

The collections API has an operator for this, called 
[`map`](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List@map[B](f:A=%3EB):scala.collection.TraversableOnce[B]). 
Like `foreach` this takes a function as its argument.

```scala
list.map(elem => elem * 2) // or, shorter:
list.map(_ * 2) 
```

`map` will apply its argument to every element of the list and return a list of the transformed elements. 
This is different from `foreach`, which does not return anything!


`filter`
--------

To only select certain elements from a list we can use 
[`filter`](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List@filter(p:A=%3EBoolean):scala.collection.TraversableOnce[A]), 
for example to only get the even numbers from a list:

```scala
list.filter(elem => elem % 2 == 0) // or, shorter:
list.filter(_ % 2 == 0)
```

`filter` applies the functional argument (called *predicate*, as it returns a `Boolean`) to each element of the list and returns 
a new list with only those elements that satisfy the predicate (i.e. for which it returns `true`).


Chaining Operators
------------------

Using basic operators like `foreach`, `map` and `filter` we can create more complex behavior.
For example: *"square all odd elements of a list, discarding the even ones and printing each element of the result"*.

```scala
List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  .filter(_ % 2 == 1)
  .map(x => x * x)
  .foreach(println)
```

Note that we can chain operators in this way because `filter` and `map` return a new `List` themselves. 
You cannot continue after `foreach`, as this operator returns [`Unit`](http://www.scala-lang.org/api/current/index.html#scala.Unit).


`fold`
------

In the [previous section](01%20higher-order-functions.md) we wrote the function `count` which returned the number of characters in a 
string that satisfy a predicate. This function does not *transform* the characters, but rather aggregates them into a single value.

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

For this the collections API has defined a number of operators. Most notable here are [`foldLeft`] and [`foldRight`]. Which one to choose 
depends entirely on what you're doing. `foldLeft` aggregates the list from left to right, whereas `foldRight` aggregates the list from 
right to left. To do so (in both cases), these operators take two parameters: 

* a seed value 
* an aggregator function

The aggregator function is first applied to the seed value and the first (`foldLeft`) or last (`foldRight`) element of the list, then
the result of that is applied to the next value, etc. The final result is the result of the last application of the aggregator.
The following pictures, taken from Wikipedia, depict this process. Note that execution in both cases proceeds from the bottom to the top.

[`foldLeft`]: http://www.scala-lang.org/api/current/index.html#scala.collection.Traversable@foldLeft[B](z:B\)\(op:\(B,A\)=%3EB):B
[`foldRight`]: http://www.scala-lang.org/api/current/index.html#scala.collection.Traversable@foldRight[B](z:B\)\(op:\(A,B\)=%3EB):B

![foldLeft](https://upload.wikimedia.org/wikipedia/commons/5/5a/Left-fold-transformation.png)

![foldRight](https://upload.wikimedia.org/wikipedia/commons/3/3e/Right-fold-transformation.png)

Typical seed-function combinations are:

 description                                | seed       | aggregator
--------------------------------------------|------------|--------------------
 the sum of a list of numbers               | `0`        | `+`
 subtracting all numbers from the seed      | any number | `-`
 the product of a list of numbers           | `1`        | `*`
 flatten a list                             | empty list | `++` (concat lists)
 turning a list of characters into a String | `""`       | `+`(String concat)
 counting the number of elements in a list  | `0`        | `+ 1`

In code:

```scala
List(1, 2, 3, 4).foldLeft(0)(_ + _)  // or List(1, 2, 3, 4).foldLeft(0)((i, sum) => i + sum)
List(1, 2, 3, 4).foldRight(0)(_ + _) // or List(1, 2, 3, 4).foldRight(0)((sum, i) => sum + i)

List(1, 2, 3, 4).foldLeft(100)(_ - _)  // or List(1, 2, 3, 4).foldLeft(100)((i, rest) => i - rest)
List(1, 2, 3, 4).foldRight(100)(_ - _) // or List(1, 2, 3, 4).foldRight(100)((rest, i) => rest - i)

List(1, 2, 3, 4).foldLeft(1)(_ * _)  // or List(1, 2, 3, 4).foldLeft(1)((i, prod) => i * prod)
List(1, 2, 3, 4).foldRight(1)(_ * _) // or List(1, 2, 3, 4).foldRight(1)((prod, i) => prod * i)

List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).foldLeft(List[Int]())(_ ++ _)  // or List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).foldLeft(List[Int]())((acc, list) => acc ++ list)
List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).foldRight(List[Int]())(_ ++ _) // or List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).foldRight(List[Int]())((list, acc) => list ++ acc)

List('a', 'b', 'c', 'd').foldLeft("")(_ + _)  // or List('a', 'b', 'c', 'd').foldLeft("")((str, c) => str + c)
List('a', 'b', 'c', 'd').foldRight("")(_ + _) // or List('a', 'b', 'c', 'd').foldRight("")((c, str) => c + str)

List('a', 'b', 'c', 'd').foldLeft(0)((count, _) => count + 1)
List('a', 'b', 'c', 'd').foldRight(0)((_, count) => count + 1)
```

The `_ + _` is just an abbriviation of the longer version of `(i, sum) => i + sum`. When using multiple underscores to access
function parameters without naming them, each underscore represents the next parameter in order.

Notice that the results of these expressions are the same, no matter the use of `foldLeft` or `foldRight`, except for *subtraction*.
This is because subtraction is not associative: `x - y` is not equal to `y - x` in general. To see what is going on, we can add
some `println` statements to the code. Note the order in which the operations are done, starting from the left versus starting
from the right.

```scala
List(1, 2, 3, 4)
  .foldLeft(20)((rest, i) => { // Notice the order of the pair components!
    println("foldLeft:")
    println(s"  i    = $i")
    println(s"  rest = $rest")

    val res = rest - i          // Notice the order of subtraction!
    println(s"  res = rest - i = $rest - $i = $res")

    res
  })

List(1, 2, 3, 4)
  .foldRight(20)((i, rest) => { // Notice the order of the pair components!
    println("foldRight:")
    println(s"  rest = $rest")
    println(s"  i    = $i")

    val res = i - rest          // Notice the order of subtraction!
    println(s"  res = rest - i = $rest - $i = $res")

    res
  })
```

Some of the more commonly used aggregations are predefined in the API:

```scala
List(1, 2, 3, 4).sum // 10
List(1, 2, 3, 4).product // 24
List(3, 4, 1, 2).max // 4
List(3, 4, 1, 2).min // 1
List('a', 'b', 'c', 'd').mkString // "abcd"
List('a', 'b', 'c', 'd').mkString(", ") // "a, b, c, d"
List('a', 'b', 'c', 'd').mkString("[", ", ", "]") // "[a, b, c, d]"
List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).flatten // List(1, 2, 3, 4, 5, 6, 7, 8, 9)
```


`reduce`
--------

`foldLeft` and `foldRight` both take two arguments: an aggregator function that transforms a subresult and 
an element from the input list into a new subresult, and seed value that initiates the aggregation, serves 
as the first subresult and is returned when the input list is empty.

```scala
def foldLeft [B](z: B)(f: (B, A) => B): B
def foldRight[B](z: B)(f: (A, B) => B): B
```

However, in some situations you do not have a seed value available but still want to aggregate. For this 
you use a variant of `fold` called `reduce`. This operator only takes the aggregator function and uses 
the first value of the input as the seed.

```scala
def reduceLeft[B >: A](f: (B, A) => B): B
def reduceRight[B >: A](op: (A, B) => B): B

List(1, 2, 3, 4).reduceLeft((b, a) => a + b) // or, shorter
List(1, 2, 3, 4).reduceLeft(_ + _)
```

(Note on the shorter version: when using multiple underscores to access function parameters without naming them, each underscore represents 
the next parameter in order, that is why you can only use them once!)

(Note that you are now more limited in the aggregator functions you can use!)

In case of an empty input list, these operators throw an exception, as they cannot return anything else. 
To fix this, Scala has a variant on this, which returns an `Option[B]` instead. If the input is empty, 
a `None` is returned, else the result is returned, wrapped in a `Some`. We will discuss `Option`s in the next section.

```scala
def reduceLeftOption[B >: A](op: (B, A) => B): Option[B]
def reduceRightOption[B >: A](op: (A, B) => B): Option[B]
```
