package workshop3.assignments

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
    list.foldLeft(true)((bool, elem) => bool && predicate(elem))
  }

  def exists[T](list: List[T])(predicate: T => Boolean): Boolean = {
    list.foldLeft(false)((bool, elem) => bool || predicate(elem))
  }

  def reverse[T](list: List[T]): List[T] = {
    list.foldLeft(List.empty[T])((acc, elem) => elem :: acc)
  }

  def find[T](list: List[T])(predicate: T => Boolean): Option[T] = {
    list.foldLeft(Option.empty[T]) {
      case (None, elem) if predicate(elem) => Option(elem)
      case (None, _) => None
      case (s@Some(_), _) => s
    }
  }
}
