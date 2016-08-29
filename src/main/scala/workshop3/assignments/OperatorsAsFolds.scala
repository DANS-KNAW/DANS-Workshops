package workshop3.assignments

object OperatorsAsFolds {

  def size[T](list: List[T]): Int = ???

  def filter[T](list: List[T])(predicate: T => Boolean): List[T] = ???

  def map[T, S](list: List[T])(f: T => S): List[S] = ???

  def forall[T](list: List[T])(predicate: T => Boolean): Boolean = ???

  def exists[T](list: List[T])(predicate: T => Boolean): Boolean = ???

  def reverse[T](list: List[T]): List[T] = ???

  def find[T](list: List[T])(predicate: T => Boolean): Option[T] = ???
}
