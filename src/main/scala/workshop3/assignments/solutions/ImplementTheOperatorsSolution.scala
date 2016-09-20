package workshop3.assignments.solutions

import scala.collection.immutable.Queue

object OperatorsAsFoldsSolution {

  def size[T](list: List[T]): Int = {
    list.foldLeft(0)((count, _) => count + 1)
  }

  def filter[T](list: List[T])(predicate: T => Boolean): List[T] = {
    list.foldRight(List.empty[T])((elem, acc) => if (predicate(elem)) elem :: acc else acc)
  }

  def map[T, S](list: List[T])(f: T => S): List[S] = {
    list.foldRight(List.empty[S])((elem, acc) => f(elem) :: acc)
  }

  def forall[T](list: List[T])(predicate: T => Boolean): Boolean = {
    // note the order of the `&&` here, first check `bool`, then do the calculation
    // that way, if `bool == false` you don't have to call `predicate(elem)`
    list.foldLeft(true)((bool, elem) => bool && predicate(elem))
  }

  def exists[T](list: List[T])(predicate: T => Boolean): Boolean = {
    // the same applied here with `||` as it did in the previous function with `&&`
    list.foldLeft(false)((bool, elem) => bool || predicate(elem))
  }

  def reverse[T](list: List[T]): List[T] = {
    list.foldLeft(List.empty[T])((acc, elem) => elem :: acc)
  }

  def find[T](list: List[T])(predicate: T => Boolean): Option[T] = {
    // note the order of pattern matching here: if you already found a result, just propagate it
    list.foldLeft(Option.empty[T]) {
      case (s@Some(_), _) => s
      case (None, elem) if predicate(elem) => Option(elem)
      case (None, _) => None
    }
  }
}

object SomeOtherImplementationsSolution {

  def map[A, B](list: List[A])(f: A => B): List[B] = {
    list.flatMap(f.andThen(List(_)))
  }

  def apply[A, B](list: List[A], fs: List[A => B]): List[B] = {
    fs.flatMap(f => list.map(a => f(a)))
  }

  def flatten[A](listOfLists: List[List[A]]): List[A] = {
    listOfLists.flatMap(identity)
  }

  def runningSum(list: List[Int]): List[Int] = {
    list.scan(0)(_ + _).drop(1)
  }

  def runningAverage(list: List[Double], n: Int): List[Double] = {
    list.scanLeft(Queue.empty[Double])((queue, d) => {
      if (queue.length == n) {
        val (_, q) = queue.dequeue
        q.enqueue(d)
      }
      else
        queue.enqueue(d)
    })
      .drop(1) // you don't need the first empty queue (seed value)
      .map(queue => queue.sum / queue.size)
  }
}
