import scala.collection.mutable.ListBuffer

// an interface for a list of integers
trait IntList {
  def add(x: Int)
}

// a basic implementation of IntList
class BasicIntList extends IntList {
  private val list = new ListBuffer[Int]

  def add(x: Int) = list += x

  // mkString makes a String-representation of a List
  // eg. List(1, 2, 3).mkString("{", " - ", "}") == {1 - 2 - 3}
  override def toString = list.mkString("[", ", ", "]")
}

val list = new BasicIntList
list.add(1)
list.add(2)
list

// a trait that extends from IntList and that doubles the numbers that are added to the list
trait Doubling extends IntList {
  abstract override def add(x: Int) = {
    super.add(2 * x)
  }
}

val list2 = new BasicIntList with Doubling
list2.add(1)
list2.add(2)
// because list2 also extends from Doubling, every element is doubled BEFORE adding it to the list
list2

// a trait that extends from IntList and that increments the numbers that are added to the list
trait Increment extends IntList {
  abstract override def add(x: Int) = {
    super.add(x + 1)
  }
}
// a trait that extends from IntList and only adds the numbers that are even
trait Filtering extends IntList {
  abstract override def add(x: Int) = {
    if (x % 2 == 0) super.add(x)
  }
}

// list3 only adds an element that is even, then increments it and only then REALLY adds it to the list
val list3 = new BasicIntList with Increment with Filtering
list3.add(1) // odd,  discarded
list3.add(2) // even, incremented => 3, added
list3.add(3) // odd,  discarded
list3.add(4) // even, incremented => 5, added
list3

// list4 increments an element, then checks whether it is even and only then REALLY adds it to the list
val list4 = new BasicIntList with Filtering with Increment
list4.add(1) // incremented => 2, even, added
list4.add(2) // incremented => 3, odd,  discarded
list4.add(3) // incremented => 4, even, added
list4.add(4) // incremented => 5, odd,  discarded
list4

// in general, read these extensions from right to left to see the actual behavior
