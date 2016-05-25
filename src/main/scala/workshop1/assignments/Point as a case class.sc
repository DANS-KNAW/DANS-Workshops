// 6. Refactor `Point` to be a case class. We can get rid of:
//      - companion object with apply
//      - vals in the class declaration
//    What we gain:
//      - equals, toString, hashCode implementations
//      - a companion object with apply (don't need to write `new`)
//      - pattern match capabilities (we'll get to that more in a later workshop)
case class Point(x: Int, y: Int) {
  def distanceTo(other: Point): Double = {
    val dx = math.abs(x - other.x)
    val dy = math.abs(y - other.y)

    math.sqrt(math.pow(dx, 2) + math.pow(dy, 2))
  }
}

object Origin extends Point(0, 0) {
  override def distanceTo(other: Point): Double = {
    math.sqrt(math.pow(other.x, 2) + math.pow(other.y, 2))
  }
}

val o1 = Origin
val o2 = Origin
o1.eq(o2)

Point(3, 4).distanceTo(Origin)

Point(3, 4).distanceTo(Point(9, 12))
