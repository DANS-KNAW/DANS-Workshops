Postcard
========

Given are a definition of a `Postcard` as a case class and lists of cities, relatives and travelers. A function 
`sendPostcardsImperative` is defined that sends a postcard from every city to all relatives (whose name starts 
with a 'G') for every traveler. All postcards are collected in a list.

1. Refactor this function to use higher-order functions instead of iterating over each element of the list.
2. Refactor this function (either the original function or the one defined in step 1) to use a single 
   for-comprehension instead.
3. Verify that all three implementations give the same set of Postcards.
4. What happens when one of the lists is actually empty. What effect does this have on the final result? Is 
   there a difference in this perspective between the results of the three implementations?
