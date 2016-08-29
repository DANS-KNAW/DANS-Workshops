#Animals
Given are three type aliasses for `Animal`, `Sound` and `Food`. Also a class `Animals` is defined. This class has a constructor with two parameters: a mapping from `Animal` to `Sound` and a mapping from `Sound` to `Food`.

Examples of these parameters are:
```scala
val animalSays = Map("Dog" -> "Woof", "Cat" -> "Meow", "Cow" -> "Mooh")
val asksForFood = Map("Woof" -> "Meat", "Meow" -> "Fish", "Mooh" -> "Grass")
```

Implement the methods defined in `Animals`. Use `Map.get(key)` to get values out of the mappings, as it returns an `Option` (`None` if the key is not present, `Some` if the key is present).

**HINT:** Follow the type signatures for Map.get and Option (especially .map and .flatMap) to get these implementations right.

```scala
Map[K, V]:
  def get(key: K): Option[V]

Option[A]:
  def flatMap[B](f: A => Option[B]): Option[B]
  def map[B](f: A => B): Option[B]
```
