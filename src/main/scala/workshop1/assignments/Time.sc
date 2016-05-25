// 1. Create a class `Time`...
class Time(val hours: Int, val minutes: Int) {
  // 2. Make sure that you can only create valid times.
  require(hours >= 0 && hours < 24)
  require(minutes >= 0 && minutes < 60)

  // 4. Implement a method isBefore...
  def isBefore(time: Time): Boolean = {
    (hours < time.hours) || (hours == time.hours && minutes < time.minutes)
  }
  // 5. create a method <...
  def <(time: Time): Boolean = isBefore(time)

  // 3. Override the `toString` method...
  override def toString = {
    def format(num: Int): String = f"$num%02d"

    s"${format(hours)}:${format(minutes)}"
  }
}
// 6. Create a companion object with 2 `apply` methods
object Time {
  def apply(hours: Int, minutes: Int) = new Time(hours, minutes)
  def apply(time: String) = {
    val Array(h, m) = time.split(":")
    new Time(h.toInt, m.toInt)
  }
}

val t1 = Time(8, 52)
val t2 = Time("15:04")

t1 < t2
t1.isBefore(t2)

t2 < t1
t2.isBefore(t1)

// 7. Refactor Time into a case class
/*
   changes:
     - no val before the parameters
     - no apply for the default parameters
   extras:
     - equals (and therefore == works correctly)
     - hashcode
     - pattern matching (unapply)
     - getters for hours and minutes
   remarks:
     - you still have to override the toString!
*/
case class Time2(hours: Int, minutes: Int) {
  require(hours >= 0 && hours < 24)
  require(minutes >= 0 && minutes < 60)

  def isBefore(time: Time2): Boolean = {
    (hours < time.hours) || (hours == time.hours && minutes < time.minutes)
  }
  def <(time: Time2): Boolean = isBefore(time)

  override def toString = {
    def format(num: Int): String = f"$num%02d"

    s"${format(hours)}:${format(minutes)}"
  }
}
object Time2 {
  def apply(time: String) = {
    val Array(h, m) = time.split(":")
    new Time2(h.toInt, m.toInt)
  }
}

val t3 = Time2("15:05")
val t4 = Time2(16, 32)

t3 < t3
t3.isBefore(t3)

t4 < t3
t4.isBefore(t3)
