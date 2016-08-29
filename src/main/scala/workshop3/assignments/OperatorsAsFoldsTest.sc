import workshop3.assignments.OperatorsAsFolds._

val emptyList = List.empty[Int]
val oneElemList = List(1)
val list = List(1, 2, 3, 4, 5)

def isEven(x: Int): Boolean = x % 2 == 0
def timesTwo(x: Int): Int = x * 2

size(emptyList)
size(oneElemList)
size(list)

filter(emptyList)(isEven)
filter(oneElemList)(isEven)
filter(List(2))(isEven)
filter(list)(isEven)

map(emptyList)(timesTwo)
map(oneElemList)(timesTwo)
map(list)(timesTwo)

forall(emptyList)(isEven)
forall(oneElemList)(isEven)
forall(List(2))(isEven)
forall(list)(isEven)
forall(list)(_ < 6)

exists(emptyList)(isEven)
exists(oneElemList)(isEven)
exists(List(2))(isEven)
exists(list)(isEven)
exists(list)(_ > 6)

reverse(emptyList)
reverse(oneElemList)
reverse(list)

find(emptyList)(isEven)
find(oneElemList)(isEven)
find(List(2))(isEven)
find(list)(isEven)
find(reverse(list))(isEven)
