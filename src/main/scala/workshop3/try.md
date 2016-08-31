#Try

In Java you have to state explicitly that a certain method can throw an exception. That is, if the exception is not a `RuntimeException`. When this exception throwing method is used in a second method, it must handle the exception by either propagating it up the stack (rethrowing) or catching it and providing an alternative value.

```java
public static void foo throws Exception {
    throw new Exception();
}

public static void bar1() throws Exception {
    foo();
}

public static void bar2() {
    try {
        foo();
    }
    catch (Exception e) {
        // do something
    }
}
```

In Scala you can throw exceptions as well, but there is no specification such as Java's `throws Exception` in the function declaration. This makes the type of your functions less safe and can cause unexpected bugs and failures. The compiler cannot check whether an error can occur somewhere and is therefore not able to warn you about it.

```scala
def foo: Unit = {
    throw new Exception()
}

// if this method is not documented properly and/or you don't have access
// to the source code, you will only find out at runtime that this function
// throws an Exception. This is not visible in the return type!
```

Scala instead provides a technique that is quite similar to its `Option` type. Whereas this type denoted the possibility of returning a `null`, a `Try` type expresses the possibility of returning an `Exception` instead of an actual value.

`Try` has two subclasses: `Success` (which holds an actual value) and `Failure` (which holds an exception). The approach here is to not *throw* the `Exception` but *return* it, wrapped in a `Failure` class.

```scala
def funckySum(a: Int, b: Int): Try[Int] = {
  val sum = a + b
  if (sum == 5) Success(sum)
  else Failure(IllegalArgumentException("sum isn't 5, sorry, can't return it :("))
}

funckySum(3, 2)         // returns Success(5)
funckySum(4, 1)         // returns Success(5)
funckySum(4, 3)         // returns Failure(IllegalArgumentException(...))
```

If some function (for instance in third party libraries) does throw exceptions, we can easily catch them by using a `Try.apply` (remember, you don't need to write the `apply`!). This function automatically catches the exception and wraps them into a `Failure`. Note that this is a fail-fast approach, just like the `try-catch` blocks!

```scala
def funckySum(a: Int, b: Int): Try[Int] = Try {
  val sum = a + b
  if (sum == 5) sum
  else throw IllegalArgumentException("sum isn't 5, sorry, can't return it :(")
}

funckySum(3, 2)         // returns Success(5)
funckySum(4, 1)         // returns Success(5)
funckySum(4, 3)         // returns Failure(IllegalArgumentException(...))
```

It should not come as a surprise that `Try` has also a number of operators defined on it, such that you can transform it in the same way as `Option` and `List`. Again we have `map`, `flatMap`, `filter` and `foreach` defined on it, which only execute the inner functions in the case a `Try` is a `Success`. In the case of a `Failure`, these lambdas are not executed and the original `Exception` is propagated.

An interesting thing happens when using `filter`. If the predicate does not hold for the value inside `Try` (in case it is a `Success`!), the `Try` will be converted into a `Failure` containing a `NoSuchElementException`. This may be useful on one hand, but can also be unintended, as you probably might want to have control over which type of exception to be 'returned' as well as the message it contains. For this reason you might as well do the filtering and predicate checking yourself and come up with a meaningful error message, as shown in the code below:

```scala
def evenNumber(n: Int): Try[Int] = {
	Try(n).filter(x => x % 2 == 0)
}

def evenNumberManually(n: Int): Try[Int] = {
	if (n % 2 == 0) Success(n)
	else Failure(new IllegalArgumentException(s"$n is not even"))
}

evenNumber(4)               // returns Success(4)
evenNumber(5)               // returns Failure(java.util.NoSuchElementException: Predicate does not hold for 5)

evenNumberManually(4)       // returns Success(4)
evenNumberManually(5)       // returns Failure(java.lang.IllegalArgumentException: 5 is not even)
```

Besides the operators mentioned above, `Try` defines a set of operators that can do error handling, which is equivalent to the `catch` block in a try-catch expression. There is a same kind of `getOrElse` operator as is defined on `Option`, which returns the value inside a `Try` if it is a `Success` and returns a default value if it is a `Failure`. However, this operator does not discriminate between the various types of exceptions that can be contained in a `Failure`.

```scala
evenNumber(4).getOrElse(2)      // returns 4
evenNumber(5).getOrElse(2)      // returns 2
```

To do so, we can use operators like `recover` and `recoverWith`. These both take a so-called '*partial function*', meaning that you do not have to define the function for every type of input. Instead you pattern match on the input and write code for the cases you want to recover from. Cases that are not covered by the pattern match will just be propagated. This means that the return type of a `recover` or `recoverWith` operator always has to return a `Try` as well. It only transforms certain failure cases into successes.

When using `recover`, you match a certain kind of exception and return a *value* that you want to have instead. This value will then be wrapped in a `Success`. In the code below you see this pattern matching and the use of `recover` in action. Since `evenNumber` returns a `NoSuchElementException` when the input is odd, it will transform to a `Success(-1)`. On the other hand, `evenNumberManually` returns a `IllegalArgumentException`, which is not covered by the pattern match. Therefore the original result is just propagated. In a sense, `recover` can be thought of as the `map` operator's equivalent for `Failure` cases.

```scala
evenNumber(4).recover {                     // returns Success(4)
	case e: NoSuchElementException => -1
}
evenNumber(5).recover {                     // returns Success(-1)
	case e: NoSuchElementException => -1
}
evenNumberManually(4).recover {             // returns Success(4)
	case e: NoSuchElementException => -1
}
evenNumberManually(5).recover {             // returns Failure(java.lang.IllegalArgumentException: 5 is not even)
	case e: NoSuchElementException => -1
}
```

On the other hand, we can use `recoverWith`. This operator looks like `recover`, but its inner function (which is again a *partial function*) doesn't return a value, but returns *another `Try`* instead. For example, in the code below, the `NoSuchElementException` returned by the `filter` in `evenNumber` is transformed into an exception with a more meaningful message.

```scala
evenNumber(4).recoverWith {         // returns Success(4)
	case e: NoSuchElementException => Failure(new IllegalArgumentException(s"the input wasn't an even number"))
}
evenNumber(5).recoverWith {         // returns Failure(java.lang.IllegalArgumentException: the input wasn't an even number)
	case e: NoSuchElementException => Failure(new IllegalArgumentException(s"the input wasn't an even number"))
}
```
