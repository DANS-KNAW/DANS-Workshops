/*
  Suppose you are asked to implement a function that counts the number of characters in a String
  (without using String.length, String.size).
  An imperative way of doing this (note: we are not doing functional programming yet!) would be to
  iterate over the String with a for-loop.
 */
def numberOfChars(string: String): Int = {
  var count = 0
  for (c <- string) {
    count += 1
  }
  count
}
numberOfChars("abcdef")
numberOfChars("abba")

/*
  You are also asked to implement a function that counts the number of a's in a String.
  Copy-paste is your friend, so you copy the code above and add an extra if-statement:
 */
def numberOfA(string: String): Int = {
  var count = 0
  for (c <- string) {
    if (c == 'a')
      count += 1
  }
  count
}
numberOfA("abcdef")
numberOfA("abba")

/*
  Finally you are asked to implement a function that counts the number of b's and c's in a String.
  Again you can copy-paste the latest String and modify it a bit:
 */
def numberOfBandC(string: String): Int = {
  var count = 0
  for (c <- string) {
    if (c == 'b' || c == 'c')
      count += 1
  }
  count
}
numberOfBandC("abcdef")
numberOfBandC("abba")

/*
  Looking back at this code makes you realize that this is not a good practice. A great way to
  refactor these functions in this case is to write a higher-order function.

  A higher-order function is a function that takes as its argument another function. This is
  different from the functions above that just take 'normal' values as their arguments.
  Higher-order functions look as follows:
    def name(f: I => O): X
  where
    'name' is the higher-order function's name
    'f' is the name of the argument (note that 'f' denotes a function!)
    'I' is the input type of the inner-function
    'O' is the output type of the inner-function
    'X' is the return type of the higher-order function
  Note that higher-order functions can also take 'normal' values as their arguments. The 'higher-order'
  just signifies that one or more arguments are functions.

  In the case of the counting examples, we could generalize the second and third function by using a
  higher-order function for the predicate in the if-statements. Writing these predicates as functions
  would look like:
 */
def charIsA(c: Char): Boolean = {
  c == 'a'
}
def charIsBorC(c: Char): Boolean = {
  c == 'b' || c == 'c'
}

/*
  Note that both these functions have an input argument of type Char and an output of type Boolean.
  We write the type of these functions as:
    Char => Boolean

  We can now use this function type as an argument in the refactored counting-function:
 */

def count(string: String, predicate: Char => Boolean) = {
  var count = 0
  for (c <- string) {
    if (predicate.apply(c)) // or 'if (predicate(c))' (you don't need to write 'apply'!)
      count += 1
  }
  count
}

/*
  We use this new 'count' function by giving it the other functions as its arguments.
 */
count("abcdef", charIsA)
count("abba", charIsA)
count("abcdef", charIsBorC)
count("abba", charIsBorC)

/*
  The first function ('numberOfChars') can also be written using 'count'. For this we define a function
  that always returns true, no matter what character it is. Then we feed this function to 'count'.
 */
def allChars(c: Char): Boolean = true
count("abcdef", allChars)
count("abba", allChars)

/*
  We do not always need to define functions like 'charIsA', 'charIsBorC' and 'allChars'. You can use
  an anonymous function (or lambda expression) as well. These look exactly like the type of the inner-function:
    (c: Char) => c == 'a'
    (c: Char) => c == 'b' || c == 'c'
    (c: Char) => true
  Basically you can think of these as the same functions as the ones before, but without a name
 */
count("abcdef", (c: Char) => c == 'a')
count("abcdef", (c: Char) => c == 'b' || c == 'c')
count("abcdef", (c: Char) => true)

/*
  In this case the compiler already knows the type of 'c', so we do not need to write that either:
    c => c == 'a'
    c => c == 'b' || c == 'c'
    c => true

  Besides that, if the argument is never used, it does not require a name either, so it can be
  replaced by an underscore:
    _ => true

  On the same note: if an argument is only used once, often the 'c => c' part can be replaced
  by an underscore as well:
    _ == 'a'
 */
count("abcdef", _ == 'a')
count("abcdef", c => c == 'b' || c == 'c')
count("abcdef", _ => true)
