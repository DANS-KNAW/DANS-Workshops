// print out a String
"hello world"

// a immutable value
val x = 5
x + 1
x
x - 2
val y = x * 2

// you can't mutate a val
//x += 1
//x = 6

// a variable
var z = 3
z + 1
z
z - 2
val z2 = z * 3

// you CAN mutate a variable
z -= 1
z
z = 6

// a function
def max(x: Int, y: Int): Int = {
	if (x > y) x
	else y
}

max(2, 6)

// a function does not need a return type
// unless required by the compiler
// also, for one-liner functions you don't need the {}
def max2(x: Int, y: Int) = if (x > y) x else y
