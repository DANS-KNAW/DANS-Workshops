#Operators as folds

Given is a number of operators that are usually defined on `List` and any other collection. Define these operators in terms of `foldLeft` or `foldRight`. Make sure the tests provided in `ImplementTheOperatorsSpec` are passing after you implemented the operators.
Hint: follow the types and let your IDE be your friend!

Side note: in a more mathematical sense, `foldRight` is often referred to as a *catamorphisms*. See [page 3 of this paper](http://eprints.eemcs.utwente.nl/7281/01/db-utwente-40501F46.pdf) for examples of how to implement the operators in this assignment.

#Some other operators

Given the function definitions in `SomeOtherImplementations`

* implement `map` in terms of `flatMap`
* implement `runningSum`
* implement `runningAverage`
    * Hint: you need to maintain some state... which operator do you need for that?
