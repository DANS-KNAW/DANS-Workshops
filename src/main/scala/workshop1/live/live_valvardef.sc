"hello \" world\n"

object Main {
  def main(args: Array[String]) {
    println("hello world"); println("abc")
  }
}

Main.main(Array(""))
val x = 5
x + 5
//x = 6
var y = 7
y + 5.5
y = 4
y += 4
y.+=(4)
y
//def max(int x, int y)
def max(x: Int, y: Int) = {
  if (x > y) x
  else y
}

max(5, 3)
max(1, 2)

