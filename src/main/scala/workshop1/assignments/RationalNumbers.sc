import java.util.NoSuchElementException

import scala.annotation.tailrec

// 1. Define a class `Rational`...
// 1.1 have a constructor that accepts every set of numerator and denominator
// 2. Write the constructor that only accepts a numerator
class Rational(n: Int, d: Int = 1) {

  // 1.3 make sure the constructor does not accept `denominator = 0`
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  // 1.2 have two final fields that store the simple form of the rational number
  val numer = n / g
  val denom = d / g

  // 1.4 calculate the simple form of a rational
  @tailrec
  private def gcd(a: Int, b: Int): Int = {
    if (b == 0) a
    else gcd(b, a % b)
  }

  // 5-7 Implement +, -, * and /
  def +(that: Rational): Rational = {
    new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
  }

  def +(i: Int): Rational = {
    new Rational(numer + i * denom, denom)
  }

  def -(that: Rational): Rational = {
    new Rational(numer * that.denom - that.numer * denom, denom * that.denom)
  }

  def -(i: Int): Rational = {
    new Rational(numer - i * denom, denom)
  }

  def *(that: Rational): Rational = {
    new Rational(numer * that.numer, denom * that.denom)
  }

  def *(i: Int): Rational = {
    new Rational(numer * i, denom)
  }

  def /(that: Rational): Rational = {
    new Rational(numer * that.denom, denom * that.numer)
  }

  def /(i: Int): Rational = {
    new Rational(numer, denom * i)
  }

  def ^(exp: Int): Rational = {
    new Rational(math.pow(numer, exp).toInt, math.pow(denom, exp).toInt)
  }

  def inverse: Rational = {
    if (numer == 0) throw new NoSuchElementException("inversion is not allowed: the numerator is zero")
    else new Rational(denom, numer)
  }

  // 4. Implement the `equals` method
  override def equals(other: Any): Boolean = other match {
    case that: Rational => numer == that.numer && denom == that.denom
    case _ => false
  }

  // 3. Implement the `toString` method...
  override def toString = {
    if (denom == 1) numer.toString
    else numer + "/" + denom
  }

  def toMixedNumeral = {
    val wholeNum = numer / denom

    if (wholeNum == 0) toString
    else s"$wholeNum ${numer % denom}/$denom"
  }
}

val r1 = new Rational(2, 4)
val r2 = new Rational(1, 2)
val r3 = new Rational(3, 4)

r1 == r2
r2 == r3

// 8. Write some equations...
r1 + r2
r2 + r3
r2 + (r3 * 2)

// 9. Why would it NOT be useful to have Rational be a case class?
/*
   A case class would automatically create public getters for all constructor parameters. However, in this case we don't want that, as we probably need to convert the input parameters into their simple form.
 */

// 10. See above, in the class definition (`^`, `inverse` and `toMixedNumeral`). Below are some test cases
r1 ^ 2
r1 ^ 1
r1 ^ 0

r1.inverse
r2.inverse
r3.inverse

r3.inverse.toMixedNumeral
