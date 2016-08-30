package workshop3.test

import org.scalatest.{FlatSpec, Matchers}
import workshop3.assignments.{OperatorsAsFolds => folds}

class OperatorsAsFoldsSpec extends FlatSpec with Matchers {

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
}
