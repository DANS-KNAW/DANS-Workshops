package workshop3.assignments

import scala.util.{Failure, Success, Try}

sealed abstract class Tree[A] {

	def map[B](f: A => B): Tree[B]

	def flatMap[B](f: A => Tree[B]): Tree[B]

	def zipWith[B](other: Tree[B]): Try[Tree[(A, B)]]
}

case class Leaf[A](a: A) extends Tree[A] {
	def map[B](f: A => B): Tree[B] = Leaf(f(a))

	def flatMap[B](f: A => Tree[B]): Tree[B] = f(a)

	def zipWith[B](other: Tree[B]): Try[Tree[(A, B)]] = {
		other match {
			case Leaf(b) => Success(Leaf((a, b)))
			case b => Failure(new IllegalArgumentException(s"this is a Leaf, while other is not ($b)"))
		}
	}
}

case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
	def map[B](f: A => B): Tree[B] = Branch[B](left.map(f), right.map(f))

	def flatMap[B](f: A => Tree[B]): Tree[B] = Branch(left.flatMap(f), right.flatMap(f))

	def zipWith[B](other: Tree[B]): Try[Tree[(A, B)]] = {
		other match {
			case l@Leaf(_) => Failure(new IllegalArgumentException(s"this is a branch while other is not ($l)"))
			case Branch(l, r) =>
				for {
					ll <- left.zipWith(l)
					rr <- right.zipWith(r)
				} yield Branch(ll, rr)
		}
	}
}
