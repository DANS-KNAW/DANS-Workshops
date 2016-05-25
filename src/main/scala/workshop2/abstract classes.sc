// we define an Expression to be a class that implements two methods: evaluate and prettyprint
// sealed -> you cannot make other extensions anywhere else than in this class
sealed abstract class Expr {
	def evaluate: Int
	def prettyPrint: String
}

case class Number(value: Int) extends Expr {
	def evaluate = value

	def prettyPrint = value.toString
}

case class Sum(lhs: Expr, rhs: Expr) extends Expr {
	def evaluate = lhs.evaluate + rhs.evaluate

	def prettyPrint = s"${lhs.prettyPrint} + ${rhs.prettyPrint}"
}

case class Product(lhs: Expr, rhs: Expr) extends Expr {
	def evaluate = lhs.evaluate * rhs.evaluate

	def prettyPrint = {
		// due to the order of operations we need to sometimes wrap a subexpression in parentheses
		def pp(expr: Expr) = expr match {
			case s: Sum => s"(${s.prettyPrint})"
			case ex => s"${ex.prettyPrint}"
		}
		s"${pp(lhs)} * ${pp(rhs)}"
	}
}

val expr1 = Product(Sum(Number(4), Number(3)), Number(2)) // (4 + 3) * 2
val expr2 = Product(Number(4), Sum(Number(3), Number(2))) // 4 * (3 + 2)
val expr3 = Product(Sum(Number(4), Number(3)), Sum(Number(2), Number(1))) // (4 + 3) * (2 + 1)
val expr4 = Sum(Number(4), Product(Number(3), Number(2))) // 4 + 3 * 2

val pp1 = expr1.prettyPrint
val pp2 = expr2.prettyPrint
val pp3 = expr3.prettyPrint
val pp4 = expr4.prettyPrint

val result1 = expr1.evaluate
val result2 = expr2.evaluate
val result3 = expr3.evaluate
val result4 = expr4.evaluate
