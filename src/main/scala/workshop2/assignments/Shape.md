# Shape
1. Define a abstract class `Shape` and case classes `Rectangle` (with parameters `width` and `height`) and `Circle` (with parameter `radius`) that inherit from `Shape`.
2. Implement an object with two functions that calculate the circumference and area of a `Shape`.
3. What would be the best way of implementing this class hierarchy as well as these functions in a strictly object oriented language like Java?
    * Give a Java implementation.
    * Port this implementation to Scala.
4. Compare these to ways of implementing. What are the advantages and disadvantages?
    * Can you add extra subclasses to either variant of `Shape` without recompiling existing code?
    * Can you add extra functions to either variant of `Shape` without recompiling existing code?
5. Would it be useful to declare `Shape` as a `sealed trait` in either of these cases?
    * Can you add extra subclasses in either of these cases without recompiling existing code?
    * Can you add extra functions in either of these cases without recompiling existing code?
