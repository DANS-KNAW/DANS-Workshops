# List and for-comprehensions

In the same way as we use `flatMap` on `Option`, we can use it on `List` as well. For example, if we want a list with tuples of all combinations of elements of two lists, we write:

```scala
val xs = List(1, 2, 3, 4)
val ys = List('a', 'b', 'c')
val tuples = xs.flatMap(x => ys.map(y => (x, y)))

// of course we can also write this as a for-comprehension:
val tuplesFC = for {
  x <- xs
  y <- ys
} yield (x, y)
```

For-comprehensions in general (so, this not only applies to `List`, but also to `Option` and other types that have `flatMap` implemented) can also be used to write queries with `filter` (called a '*guard*'). This is done using an if statement as shown below. Note here that this is NOT an if/else statement, but only to filter elements that satisfy the predicate! Also note that you don't use parentheses here, unlike the if/else statement.

```scala
def factors(n: Int) = (1 to n).filter(x => n % x == 0)

def factorsFC(n: Int) = {
  for {
    x <- 1 to n
    if n % x == 0
  } yield x
}
factors(5)
factorsFC(5)
```

If you want to keep the list intact rather than draw from it using the `<-`, you can use an `=`. In the following example we use this to get all prime numbers from a list.
* `x <- xs`                         we take each number from the input list
* `allFactors = factors(x)`         calculate all the dividers of a number;
                                    keep the list intact; do not draw from the list!
* `if allFactors.size == 2`         only keep the lists of factors that have size 2;
                                    i.e. keep the numbers that have only two dividers
* `if allFactors(0) == 1`
* `if allFactors(1) == x`           only keep the numbers that have 1 and themselves as divisers
* `yield x`                         put x in the output (only if it satisfies all three predicates)

```scala
def primes(xs: List[Int]): List[Int] = {
  for {
    x <- xs
    allFactors = factors(x) // or factorsFC(x) for that matter
    if allFactors.size == 2
    if allFactors(0) == 1
    if allFactors(1) == x
  } yield x
}
primes(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))
```
