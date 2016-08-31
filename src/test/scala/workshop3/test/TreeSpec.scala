package workshop3.test

import org.scalatest.{FlatSpec, Matchers}
import workshop3.assignments.{Branch, Leaf}

import scala.util.{Failure, Success}

class TreeSpec extends FlatSpec with Matchers {

	"map" should "transform a tree that is a single leaf according to the specified function" in {
		Leaf("a").map(_.toUpperCase) shouldBe Leaf("A")
	}

	it should "transform a tree according to the specified function" in {
		val tree = Branch(
			Leaf("a"),
			Branch(
				Leaf("b"),
				Leaf("c")
			)
		)
		val expected = Branch(
			Leaf("A"),
			Branch(
				Leaf("B"),
				Leaf("C")
			)
		)

		tree.map(_.toUpperCase) shouldBe expected
	}

	"flatMap" should "should replace every leaf in tree1 with a copy of tree2 if tree2 is just a leaf" in {
		val tree1 = Branch(
			Leaf("a"),
			Leaf("b")
		)
		val tree2 = Leaf("c")

		val result = for {
			s1 <- tree1
			s2 <- tree2
		} yield s"($s1, $s2)"
		val expected = Branch(
			Leaf("(a, c)"),
			Leaf("(b, c)")
		)

		result shouldBe expected
	}

	it should "should replace every leaf in tree1 with a copy of itself" in {
		val tree1 = Branch(
			Leaf("a"),
			Leaf("b")
		)

		val result = for {
			s1 <- tree1
			s2 <- tree1
		} yield s"($s1, $s2)"
		val expected = Branch(
			Branch(
				Leaf("(a, a)"),
				Leaf("(a, b)")
			),
			Branch(
				Leaf("(b, a)"),
				Leaf("(b, b)")
			)
		)

		result shouldBe expected
	}

	it should "should replace every leaf in tree1 with a copy of tree2" in {
		val tree1 = Branch(
			Leaf("a"),
			Leaf("b")
		)
		val tree2 = Branch(
			Leaf("c"),
			Branch(
				Leaf("d"),
				Leaf("e")
			)
		)

		val result = for {
			s1 <- tree1
			s2 <- tree2
		} yield s"($s1, $s2)"
		val expected = Branch(
			Branch(
				Leaf("(a, c)"),
				Branch(
					Leaf("(a, d)"),
					Leaf("(a, e)")
				)
			),
			Branch(
				Leaf("(b, c)"),
				Branch(
					Leaf("(b, d)"),
					Leaf("(b, e)")
				)
			)
		)

		result shouldBe expected
	}

	"zipWith" should "zip successfully if the trees have the same shape" in {
		val tree1 = Branch(Leaf("a"), Leaf("b"))
		val tree2 = Branch(Leaf(1), Leaf(2))

		val result = tree1.zipWith(tree2)
		val expected = Branch(Leaf(("a", 1)), Leaf(("b", 2)))

		result shouldBe a[Success[_]]
		result.get shouldBe expected
	}

	it should "fail if the left tree is bigger than the right tree" in {
		val tree1 = Branch(Leaf("a"), Leaf("b"))
		val tree2 = Leaf(1)

		tree1.zipWith(tree2) shouldBe a[Failure[_]]
	}

	it should "fail if the left tree is smaller than the right tree" in {
		val tree1 = Leaf(1)
		val tree2 = Branch(Leaf("a"), Leaf("b"))

		tree1.zipWith(tree2) shouldBe a[Failure[_]]
	}
}
