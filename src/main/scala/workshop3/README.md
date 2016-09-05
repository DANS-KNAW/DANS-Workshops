Workshop 3
==========

In this workshop we cover collections, `Option`, `Try` and higher-order functions on these classes.
Please, read through the material in the following order, before proceeding to the [assignments](assignments).

1. [higher-order functions](01%20higher-order-functions.md) - *getting to know what a higher-order function is, how it looks and how you use it*
2. [some basic higher-order functions in Scala](02%20functional-operators.md)
3. [`Option`](03%20option.md) - *introducing the `Option` type as a better alternative to `null` values*
4. [`flatMap` and for-comprehension on `Option`](04%20option-flatmap.md)
5. [for-comprehension on `List`](05%20list-for-comprehensions.md)
6. [`Try`](06%20try.md) - *introducting the `Try` type as **the** way of handling errors in a functional way*
7. [other collections](07%20other-collections.md) - *conversion between Java and Scala collections and some more operators*


Further reading/watching (totally optional)
-------------------------------------------

* [Scala API](http://www.scala-lang.org/api/current/) - *Documentation of the Scala API. Make sure you have this page open in your browser **all** the time!*
* [Monads in pictures](http://adit.io/posts/2013-04-17-functors,_applicatives,_and_monads_in_pictures.html) - *The `map` 
    and `flatMap` operators that we used in this workshop are formalized in the concepts of `Functor` and `Monad`. 
    This is a good post to understand what these do and how `map` and `flatMap` are supposed to behave.*
* [Learn You a Haskell for Great Good](http://learnyouahaskell.com/) - *For the ones that are really 
    interested in functional programming, this book is **the** place to start!*
* [Scala Monads](https://youtu.be/Mw_Jnn_Y5iA) - *A good starter explanation of the `Option`, `Try` 
    and `List` monads <sup>that shows remarkable similarities with our explanation :stuck_out_tongue_winking_eye:...</sup>*
