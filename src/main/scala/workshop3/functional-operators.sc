// consider the following 'imperative' code that prints all values of a list:
val list = List(1, 2, 3, 4)
for (elem <- list) {
  println(elem)
}

// we can also write this using a higher-order function (or operator) that is defined on List
// and use the 'println' function as its argument:
list.foreach(elem => println(elem))

// 'foreach' will apply this inner-function to every element of the list

////////////////////////////

// the following 'imperative' code doubles all values of a list
var result: List[Int] = Nil
for (elem <- list) {
  val doubled = elem * 2
  result = result :+ doubled
}
result

// list has an operator for this, called 'map'. Like 'foreach' this takes a function as its argument.
list.map(elem => elem * 2)

// 'map' will apply this inner-function to every element of the list and return a list of the
// transformed elements.
// this is different from 'foreach', which does NOT return anything!

////////////////////////////

// to only select certain elements from a list we can use 'filter',
// for example to only get the even numbers from a list:
list.filter(elem => elem % 2 == 0)

// again, 'filter' applies this inner-function to each element of the list and returns a new list
// with only those elements that satisfy the predicate (eg. to which the inner-function returns 'true')

////////////////////////////

// using basic operators like 'foreach', 'map' and 'filter' we can create more complex behavior.
// for example: square all odd elements of a list, discarding the even ones and printing each element of the result
List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  .filter(x => x % 2 == 1)
  .map(x => x * x)
  .foreach(x => println(x))

// note that we can chain operators in this way because 'filter' and 'map' return a new list themselves.
// you cannot continue after 'foreach', as this operator returns Unit

////////////////////////////

/*
  In the previous section (higher-order functions) we wrote the function 'count' which returns the
  number of characters in a String that satisfy a predicate.
  In this code we do not transform the characters, but rather aggregate them into a single value.
 */
def count(string: String, predicate: Char => Boolean) = {
  var count = 0
  for (c <- string) {
    if (predicate.apply(c)) // or 'if (predicate(c))' (you don't need to write 'apply'!)
      count += 1
  }
  count
}

/*
  For this we can use two operators: 'foldLeft' and 'foldRight'. Which one to choose depends entirely
  on what you're doing. 'foldLeft' aggregates the list from left to right, whereas
  'foldRight' aggregates the list from right to left. To do so (in both cases), these operators take
  two parameters: a seed value with which the aggregation is started and an aggregation function which
  combines the seed and a value from a list into a new seed (or temporary result).

  Typical seed-function combinations are:
    description                               | seed       | aggregator
    ------------------------------------------|------------|------------------
    the sum of a list of numbers              | 0          | +
    subtracting all numbers from the seed     | any number | -
    the product of a list of numbers          | 1          | *
    flatten a list                            | empty list | ++ (concat lists)
    making a list of characters into a String | ""         | + (String concat)
    counting the number of elements in a list | 0          | + 1
 */

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

/*
  Notice how subtraction is different for foldLeft and foldRight.
  To see what's going on we can add some println statements. Note the order in which the operations
  are done, starting from the left vs starting from the right.
 */

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

////////////////////////////

// some commonly used aggregations are already predefined in the API. Some examples of this are:
List(1, 2, 3, 4).sum
List(1, 2, 3, 4).product
List(3, 4, 1, 2).max
List(3, 4, 1, 2).min
List('a', 'b', 'c', 'd').mkString
List('a', 'b', 'c', 'd').mkString(", ")
List('a', 'b', 'c', 'd').mkString("[", ", ", "]")
List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).flatten
