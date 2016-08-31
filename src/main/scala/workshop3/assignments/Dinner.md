#Dinner
In this assignment you are going to prepare a dinner. All ingredients and steps are denoted in type aliases (see `package.scala`) and functions (see `Dinner.scala`). However, any of these functions (except for `orderPizza` and `prepareDinner`) might return a `null`.
1. Given is an imperative implementation of the function `prepareDinner` in the abstract class `ImperativeDinner`. Test that this function works correctly by creating a subclass of `ImperativeDinner` and implementing the abstract methods by returning `null` for any (0 or more) of them and actual values for the others.
2. Write a new class `HigherOrderDinner` and refactor the `ImperativeDinner` class. Hint: use `Option` and its (higher-order) operators. Also make sure your implementation is correct using the same method as in step 1.
3. Write a third class `DinnerWithForComprehension` and refactor the use of higher-order functions in `HigherOrderDinner` to a single for-comprehension.
