// 3b. Scala port of the Java code.
//     Notice that this implementation is similar to what we saw in `abstract classes.sc`
abstract class Shape {
	def circumference: Double
	def area: Double
}
case class Rectangle(w: Double, h: Double) extends Shape {
	def circumference: Double = 2 * (w + h)

	def area: Double = w * h
}
case class Circle(radius: Double) extends Shape {
	def circumference: Double = 2 * math.Pi * radius

	def area: Double = math.Pi * math.pow(radius, 2.0)
}

val rectangle = Rectangle(2, 1)
val circle = Circle(1)

rectangle.circumference
circle.circumference

rectangle.area
circle.area

/*
  4a.   You can add extra subclasses in the latter case, which is not possible in the former case.
        This is due to the pattern match you do in each of those functions.
  4b.   You can add extra functionality in the former case (by providing another object that matches
        on the various kinds of shapes. In the latter case you cannot add extra functionality.
  Note: Another advantage of the latter is that you can do method chaining:
        Circle(1).circumference.floor.toString
        As in the former case this would be somewhat less readable:
        circumference(Circle(1)).floor.toString
  Note: The problem of either of these styles is that you can either not add extra subclasses or
  			add extra functionality. This is called the 'Expression Problem'. If you are interested,
  			there is lots of material on the Internet on this topic. The talk by Ralf Lämmel at Channel9
  			is a good starting point: https://goo.gl/jFvcqZ
 */

/*
  5.  You could either make this a `sealed trait` or `sealed class` if you want to prevent others
      from making extra subclasses of your trait or abstract class. In the case of `Shape` this is
      not really practical as there can be many more shapes than a rectangle and a circle.
      An example for which the `sealed` keyword is ideal is the `Option` type, which has either a
      value or a null pointer. We will discuss the `Option` in a later workshop.
  5a. In both cases you cannot add extra subclasses to the class hierarchy except by editing the
      original file.
  5b. The `sealed` keyword doesn't have anything to do with adding extra functionality. Answer 4b
      applies here.
 */

