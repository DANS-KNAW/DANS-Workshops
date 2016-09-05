Other collections
=================

So far we have seen `List` as the main example of collections. However, just as there are many implementations 
for collections in Java, there as also many variants of them in Scala. This section will cover conversions 
between Java's and Scala's collections and list a number of useful operators that are commonly used in collections.

 
Conversion
----------

While using Scala, you are quick to discover that some external libraries you use are written in Java and 
therefore can return Java collections (hereafter called '*jCollection*'). However, you instead want to use 
Scala collections (hereafter called '*sCollection*') as these define more operators to work with in a functional 
way. On the other hand, some Java based libraries explicitly ask for a jCollection as their input, while you 
have a sCollection at hand. Scala provides special operators that do these conversions for you. First make 
sure you import `scala.collection.JavaConverters._`. This adds extra methods to your collections to transform 
one into the other.

```scala
import scala.collection.JavaConverters._

val jList = List(1, 2, 3, 4).asJava
val sList = java.util.Arrays.asList(1, 2, 3, 4).asScala
```

**Note:** Besides `scala.collection.JavaConverters` there is a `scala.collection.JavaConversions`. This does 
the transformation for you without explicitly typing `asJava` or `asScala`. However, in some cases this may 
lead to unexpected behavior. Therefore **always** use the `JavaConverters` method of conversion! For an example, 
see [this explaination](https://github.com/DANS-KNAW/easy-bag-store/pull/2/files/4884897a43c75550ec6742e08617b8fbc74f0f96#r68929446) 
on the `easy-bag-store` project.


List vs Seq
-----------

A common misconception between Java and Scala is the naming of collections. Java defines an `Iterable` with 
subtype `Collection`, which itself is the supertype of the `List`, `Set` and `Queue` interfaces (as well as some 
other classes). The `List` interface then goes on to be the supertype of many implementations, such as `ArrayList`, 
`LinkedList` and `Vector`. *Note that these implementations are all (as of Java 8) mutable collections!*

![Java Collection hierarchy](http://www.hsufengko.com/uploads/8/0/5/7/8057674/9060885_orig.jpg)

Scala has a much more complicated structure in its collections API (we don't recommend studying this too much if 
you don't need to!), but the gist is that it defines an `Iterable` similar to the one in Java. This trait has a 
number of subtypes, among which are `Seq`, `Map` and `Set` (note that Scala skips Java's `Collection` step and 
considers a `Map` to be a collection as well). The `Seq` trait in Scala is comparable to Java's `List` interface. 
This trait is the supertype of (amongst others) the `List` class, which is equivalent to Java's `LinkedList`!

|              |Java        |Scala |
|--------------|------------|------|
|Interface     |`List`      |`Seq` |
|Implementation|`LinkedList`|`List`|
 
 ![Scala Collection hierarchy](http://docs.scala-lang.org/resources/images/collections.immutable.png)
 
Notice in this image that also Scala's `String` class is in a sense part of the collections API. This makes 
sense, as a `String` is actually just a sequence of `Char`s. The main purpose of this is to be able to have 
the operators that are defined on collections as well on a `String`.

In this same way we can view the `Option` as a collection as well. The `None` case corresponds to an empty collection, 
whereas `Some(a)` corresponds to a collection with one element `a` in it. In fact, Scala does define the `Option` 
as a collection, so you can view it as such and mix it in in case you need to!

Finally, a `Map` is viewed as collection of key-value-tuples. Therefore operators like `map`, `filter`, `flatMap` 
(and many more) have a function-argument like `(K, V) => A`, meaning that their input consists of both the key and 
the value. 


Operators on collections
------------------------

In the previous sections we have discussed various operators on List, mainly `map`, `filter` and `flatMap`, which are required for doing for-comprehensions. However, there are many more operators on collections. We encourage you to have a look in the corresponding ScalaDocs of [`Seq`](http://www.scala-lang.org/api/current/#scala.collection.Seq), [`Map`](http://www.scala-lang.org/api/current/#scala.collection.Map), [`List`](http://www.scala-lang.org/api/current/#scala.collection.immutable.List), etc. In this section we will highlight a couple of the most commonly used operators. We encourage you to experiment a bit with these operators, such that you have a good understanding of their behavior.

* `drop(n: Int)` discards the first `n` elements from the collection and only processes the remaining elements 
* `dropWhile(p: A => Boolean)` discards all elements as long as they satisfy the predicate `p`. Once the predicate is satisfied, all remaining elements will be used in (optional) further processing
* `exists(p: A => Boolean)` tests whether *at least one* element in the collection satisfies the predicate `p`
* `forall(p: A => Boolean)` tests whether *all* elements in the collection satisfy the predicate `p`
* `groupBy[K](f: A => K)` collects all elements of the collection into various groups, based on a common key of type `K`. The results are collected in a `Map[K, List[A]]`
* `partition(p: A => Boolean)` separates the elements that satisfy the predicate `p` from the ones that don't and collects the results in two lists that are returned as a tuple
* `scanLeft/scanRight` does the same as `foldLeft/foldRight` but keeps all intermediate results. This is useful to maintain some state throughout the sequence of operators. 
* `span(p: A => Boolean)` splits a list into a prefix and suffix according to the predicate `p` 
* `take(n: Int)` keeps the first `n` elements and discards the rest of the collection
* `takeWhile(p: A => Boolean)` keeps all elements as long as they satisfy the predicate `p`
* `zip(that: Iterable[B])` merges the collection pairwise with another collection `that` into a collections of tuples `(A, B)`
* `zipWithIndex` combines the elements of the collection with their index, starting at 0

Finally we refer to [this blog post](https://pavelfatin.com/scala-collections-tips-and-tricks/) for tips & tricks and some best practices regarding the use of the operators defined on collections, `Option` and `Try`.
