// 1. Define a class `Point` that describes an (x,y) coordinate.
class Point(val x: Int, val y: Int) {
  // 5. Implement a function `distanceTo`...
  def distanceTo(other: Point): Double = {
    val dx = math.abs(x - other.x)
    val dy = math.abs(y - other.y)

    math.sqrt(math.pow(dx, 2) + math.pow(dy, 2))
  }
}
// 2. Create a companion object such that you can create instances of `Point` without writing new.
object Point {
  def apply(x: Int, y: Int) = new Point(x, y)
}
// 3. Create a singleton object `Origin` that represents the (0,0) coordinate.
object Origin extends Point(0, 0) {
  // extra: you can simplify the distanceTo function in this special case
  override def distanceTo(other: Point): Double = {
    math.sqrt(math.pow(other.x, 2) + math.pow(other.y, 2))
  }
}

// 4. Check that two instances of `Origin` refer to the same object in memory.
val o1 = Origin
val o2 = Origin
o1.eq(o2)

Point(3, 4).distanceTo(Origin)

Point(3, 4).distanceTo(Point(9, 12))
