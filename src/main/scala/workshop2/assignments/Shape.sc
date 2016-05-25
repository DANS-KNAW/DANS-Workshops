import ShapeCalc._

// 1. class definitions
abstract class Shape
case class Rectangle(w: Double, h: Double) extends Shape
case class Circle(radius: Double) extends Shape

// 2. Shape functionality
object ShapeCalc {
  def circumference(shape: Shape): Double = {
    shape match {
      case Rectangle(w, h) => 2 * (w + h)
      case Circle(r) => 2 * math.Pi * r
    }
  }

  def area(shape: Shape): Double = {
    shape match {
      case Rectangle(w, h) => w * h
      case Circle(r) => math.Pi * math.pow(r, 2.0)
    }
  }
}

// 2a. Some testing
val rectangle = Rectangle(2, 1)
val circle = Circle(1)

circumference(rectangle)
circumference(circle)

area(rectangle)
area(circle)

// 3a. See the ShapeDemo.java file

// 3b, 4. and 5. See the ShapeOO.sc file
