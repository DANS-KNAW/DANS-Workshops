package workshop3.assignments

import scala.util.{Failure, Success, Try}

sealed abstract class Tree[A] {

	def map[B](f: A => B): Tree[B]

	def flatMap[B](f: A => Tree[B]): Tree[B]

	def zipWith[B](other: Tree[B]): Try[Tree[(A, B)]]
}

case class Leaf[A](a: A) extends Tree[A] {
	def map[B](f: A => B): Tree[B] = ???

	def flatMap[B](f: A => Tree[B]): Tree[B] = ???

	def zipWith[B](other: Tree[B]): Try[Tree[(A, B)]] = ???
}

case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
	def map[B](f: A => B): Tree[B] = ???

	def flatMap[B](f: A => Tree[B]): Tree[B] = ???

	def zipWith[B](other: Tree[B]): Try[Tree[(A, B)]] = ???
}
