#Functional operators

##foreach
Consider the following 'imperative' code that prints all values of a list:

```scala
val list = List(1, 2, 3, 4)
for (elem <- list) {
  println(elem)
}
```

We can also write this using a higher-order function (or operator) that is defined on List and use the 'println' function as its argument:

```scala
list.foreach(elem => println(elem))
```

`foreach` will apply this inner-function to every element of the list.

##map
The following 'imperative' code doubles all values of a list and collects the results in a new list:

```scala
var result: List[Int] = Nil
for (elem <- list) {
  val doubled = elem * 2
  result = result :+ doubled
}
result
```

The collections API has an operator for this, called `map`. Like `foreach` this takes a function as its argument.

```scala
list.map(elem => elem * 2)
```

`map` will apply this inner-function to every element of the list and return a list of the transformed elements. This is different from 'foreach', which does NOT return anything!

##filter
To only select certain elements from a list we can use 'filter', for example to only get the even numbers from a list:

```scala
list.filter(elem => elem % 2 == 0)
```

`filter` applies the inner-function (called *predicate*) to each element of the list and returns a new list with only those elements that satisfy the predicate (eg. for which the inner-function returns `true`)

##chaining operators
Using basic operators like 'foreach', 'map' and 'filter' we can create more complex behavior. For example: "*square all odd elements of a list, discarding the even ones and printing each element of the result*".

```scala
List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  .filter(x => x % 2 == 1)
  .map(x => x * x)
  .foreach(x => println(x))
```

Note that we can chain operators in this way because 'filter' and 'map' return a new `List` themselves. You cannot continue after 'foreach', as this operator returns `Unit`.

##fold
In the [previous section](higher-order-functions.md) we wrote the function `count` which returns the number of characters in a String that satisfy a predicate. This function does not *transform* the characters, but rather aggregates them into a single value.

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

For this the collections API has defined a number of operators. Most notable here are `foldLeft` and `foldRight`. Which one to choose depends entirely on what you're doing. `foldLeft` aggregates the list from left to right, whereas `foldRight` aggregates the list from right to left. To do so (in both cases), these operators take two parameters: a seed value with which the aggregation is started and an aggregation function which combines the seed and a value from a list into a new seed (or temporary result).

![foldLeft](https://upload.wikimedia.org/wikipedia/commons/5/5a/Left-fold-transformation.png)

![foldRight](https://upload.wikimedia.org/wikipedia/commons/3/3e/Right-fold-transformation.png)

Typical seed-function combinations are:

|description                               | seed       | aggregator        |
|------------------------------------------|------------|-------------------|
|the sum of a list of numbers              | 0          | +                 |
|subtracting all numbers from the seed     | any number | -                 |
|the product of a list of numbers          | 1          | *                 |
|flatten a list                            | empty list | ++ (concat lists) |
|making a list of characters into a String | ""         | + (String concat) |
|counting the number of elements in a list | 0          | + 1               |

```scala
List(1, 2, 3, 4).foldLeft(0)(_ + _)  // or (i, sum) => i + sum
List(1, 2, 3, 4).foldRight(0)(_ + _) // or (sum, i) => sum + i

List(1, 2, 3, 4).foldLeft(100)(_ - _)  // or (i, rest) => i - rest
List(1, 2, 3, 4).foldRight(100)(_ - _) // or (rest, i) => rest - i

List(1, 2, 3, 4).foldLeft(1)(_ * _)  // or (i, prod) => i * prod
List(1, 2, 3, 4).foldRight(1)(_ * _) // or (prod, i) => prod * i

List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).foldLeft(List[Int]())(_ ++ _)  // or (acc, list) => acc ++ list
List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).foldRight(List[Int]())(_ ++ _) // (list, acc) => list ++ acc

List('a', 'b', 'c', 'd').foldLeft("")(_ + _)  // or (str, c) => str + c
List('a', 'b', 'c', 'd').foldRight("")(_ + _) // or (c, str) => c + str

List('a', 'b', 'c', 'd').foldLeft(0)((count, _) => count + 1)
List('a', 'b', 'c', 'd').foldRight(0)((_, count) => count + 1)
```

Notice that the result of subtraction is different for `foldLeft` and `foldRight`. To see what's going on we can add some `println` statements to the code. Note the order in which the operations are done, starting from the left vs starting from the right.

```scala
List(1, 2, 3, 4)
  .foldLeft(20)((rest, i) => {
    println("foldLeft:")
    println(s"  i    = $i")
    println(s"  rest = $rest")

    val res = i - rest
    println(s"  res = i - rest = $i - $rest = $res")

    res
  })

List(1, 2, 3, 4)
  .foldRight(20)((i, rest) => {
    println("foldRight:")
    println(s"  rest = $rest")
    println(s"  i    = $i")

    val res = rest - i
    println(s"  res = rest - i = $rest - $i = $res")

    res
  })
```

Some of the more commonly used aggregations are predefined in the API:
```scala
List(1, 2, 3, 4).sum                                        = 10
List(1, 2, 3, 4).product                                    = 24
List(3, 4, 1, 2).max                                        = 4
List(3, 4, 1, 2).min                                        = 1
List('a', 'b', 'c', 'd').mkString                           = "abcd"
List('a', 'b', 'c', 'd').mkString(", ")                     = "a, b, c, d"
List('a', 'b', 'c', 'd').mkString("[", ", ", "]")           = "[a, b, c, d]
List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).flatten   = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
```

##reduce
`foldLeft` and `foldRight` both take two arguments: an aggregator function that transforms a subresult and an element from the input list into a new subresult, and seed value that initiates the aggregation, surves as the first subresult and is returned when the input list is empty.

```scala
def foldLeft [B](z: B)(f: (B, A) => B): B
def foldRight[B](z: B)(f: (A, B) => B): B
```

However, in some situations you do not have a seed value available but still want to aggregate. For this you use a variant of `fold` called `reduce`. This operator only takes the aggregator function and uses the first value of the input as the seed.

```scala
def reduceLeft[B >: A](f: (B, A) => B): B
def reduceRight[B >: A](op: (A, B) => B): B

List(1, 2, 3, 4).reduceLeft(_ + _)
```

In case of an empty input list, these operators throw an exception, as they cannot return anything else. To fix this, Scala has a variant on this, which returns an `Option[B]` instead. If the input is empty, a `None` is returned, else the result is returned, wrapped in a `Some`.

```scala
def reduceLeftOption[B >: A](op: (B, A) => B): Option[B]
def reduceRightOption[B >: A](op: (A, B) => B): Option[B]
```
