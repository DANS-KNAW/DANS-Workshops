package workshop3.test

import org.scalatest.{FlatSpec, Matchers}
import workshop3.assignments.solutions.{OperatorsAsFoldsSolution => folds, SomeOtherImplementationsSolution => others}

class ImplementTheOperatorsSpec extends FlatSpec with Matchers {

  val emptyList = List.empty[Int]
  val oneElemList = List(1)
  val list = List(1, 2, 3, 4, 5)

  def isEven(x: Int): Boolean = x % 2 == 0
  def timesTwo(x: Int): Int = x * 2

  "size" should "be 0 if the list is empty" in {
    folds.size(emptyList) shouldBe 0
  }

  it should "be 1 if the list contains one element" in {
    folds.size(oneElemList) shouldBe 1
  }

  it should "be 5 if the list contains 5 elements" in {
    folds.size(list) shouldBe 5
  }

  "filter" should "return an empty list if the input list is empty" in {
    folds.filter(emptyList)(isEven) shouldBe empty
  }

  it should "return an empty list if no elements satisfy the predicate" in {
    folds.filter(oneElemList)(isEven) shouldBe empty
  }

  it should "return the list of numbers that satisfy the predicate" in {
    folds.filter(list)(isEven) shouldBe List(2, 4)
  }

  "map" should "return an empty list if the input is empty" in {
    folds.map(emptyList)(timesTwo) shouldBe empty
  }

  it should "return a list with one element (doubled) with an input of size 1" in {
    folds.map(oneElemList)(timesTwo) shouldBe List(2)
  }

  it should "return a list with all elements doubled" in {
    folds.map(list)(timesTwo) shouldBe List(2, 4, 6, 8, 10)
  }

  "forall" should "return true on an empty list, no matter the predicate" in {
    folds.forall(emptyList)(isEven) shouldBe true
  }

  it should "return false if the list contains 1 odd element" in {
    folds.forall(oneElemList)(isEven) shouldBe false
  }

  it should "return true if the list contains 1 even element" in {
    folds.forall(List(2))(isEven) shouldBe true
  }

  it should "return false if the list contains odd elements" in {
    folds.forall(list)(isEven) shouldBe false
  }

  it should "return true if all elements satisfy the predicate" in {
    folds.forall(list)(_ < 6) shouldBe true
  }

  "exists" should "return false on an empty list" in {
    folds.exists(emptyList)(isEven) shouldBe false
  }

  it should "return false if the list contains 1 odd element" in {
    folds.exists(oneElemList)(isEven) shouldBe false
  }

  it should "return true if the list contains 1 even element" in {
    folds.exists(List(2))(isEven) shouldBe true
  }

  it should "return true if the list contains any even elements" in {
    folds.exists(list)(isEven) shouldBe true
  }

  it should "return false if no elements satisfy the predicate" in {
    folds.exists(list)(_ > 6) shouldBe false
  }

  "reverse" should "return an empty list if the list is empty" in {
    folds.reverse(emptyList) shouldBe empty
  }

  it should "return the same list if the list has one element" in {
    folds.reverse(oneElemList) shouldBe oneElemList
  }

  it should "return the reversed list" in {
    folds.reverse(list) shouldBe List(5, 4, 3, 2, 1)
  }

  "find" should "return None if the list is empty" in {
    folds.find(emptyList)(isEven) shouldBe None
  }

  it should "return None if the list has 1 element and it does not satisfy the predicate" in {
    folds.find(oneElemList)(isEven) shouldBe None
  }

  it should "return the element if the list has 1 element and it satisfies the predicate" in {
    folds.find(List(2))(isEven) shouldBe Some(2)
  }

  it should "return the first element satisfying the predicate" in {
    folds.find(list)(isEven) shouldBe Some(2)
  }

  it should "return the last element satisfying the predicate if the list is reversed first" in {
    folds.find(folds.reverse(list))(isEven) shouldBe Some(4)
  }

  "map" should "(implemented using flatMap) return an empty list if the input is empty" in {
    others.map(emptyList)(timesTwo) shouldBe empty
  }

  it should "(implemented using flatMap) return a list with one element (doubled) with an input of size 1" in {
    others.map(oneElemList)(timesTwo) shouldBe List(2)
  }

  it should "(implemented using flatMap) return a list with all elements doubled" in {
    others.map(list)(timesTwo) shouldBe List(2, 4, 6, 8, 10)
  }

  val f1 = (x: Int) => 2 * x
  val f2 = (x: Int) => 3 * x
  val f3 = f1 andThen f2

  "apply" should "return an empty list when the first input is empty" in {
    others.apply(Nil, List(f1)) shouldBe Nil
  }

  it should "return an empty list when the second input is empty" in {
    others.apply(List(1, 2, 3), Nil) shouldBe Nil
  }

  it should "return an empty list when both inputs are empty" in {
    others.apply(Nil, Nil) shouldBe Nil
  }

  it should "apply all functions in the second input to all elements in the first input (cross product)" in {
    others.apply(List(1, 2, 3, 4), List(f1, f2, f3)) shouldBe List(2, 4, 6, 8, 3, 6, 9, 12, 6, 12, 18, 24)
  }

  "flatten" should "return the empty list when the input is empty" in {
    others.flatten(Nil) shouldBe Nil
  }

  it should "return the empty list when the input contains empty lists only" in {
    others.flatten(List(Nil, Nil, Nil)) shouldBe Nil
  }

  it should "return the concatenation of all lists in the input" in {
    others.flatten(List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))) shouldBe List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  }

  it should "return the concatenation of all lists in the input and discard the empty lists" in {
    others.flatten(List(List(1, 2, 3), Nil, List(4, 5, 6))) shouldBe List(1, 2, 3, 4, 5, 6)
  }

  "runningSum" should "return an empty list if the input is empty" in {
    others.runningSum(emptyList) shouldBe empty
  }

  it should "return a list with one element with an input of size 1 and this element should be the same as the input" in {
    others.runningSum(oneElemList) shouldBe oneElemList
  }

  it should "return a list with the sums of all input elements" in {
    others.runningSum(list) shouldBe List(1, 3, 6, 10, 15)
  }

  "runningAverage" should "return an empty list if the input is empty" in {
    others.runningAverage(List.empty[Double], 2) shouldBe empty
  }

  it should "return a the same list as the input if the input size is 1" in {
    others.runningAverage(List(1.0), 2) shouldBe List(1.0)
  }

  it should "return the same list as the input if the number of elements to average over is 1" in {
    others.runningAverage(List(1.0, 2.0, 3.0, 4.0), 1) shouldBe List(1.0, 2.0, 3.0, 4.0)
  }

  it should "return the list of averages if the number of elements to average over is larger than 1" in {
    others.runningAverage((1 to 10).map(_.toDouble).toList, 2) shouldBe List(1.0, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5)
  }
}
