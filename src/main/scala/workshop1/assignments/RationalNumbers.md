# Rational numbers
1. Define a class `Rational` that denotes a rational number with a numerator and denominator. We want to store the rational in its 'simple' form. So 4/8 is stored as 1/2. In order to do so:
    1. have a constructor that accepts every set of numerator and denominator
    2. make sure the constructor does not accept `denominator = 0`
    3. have two final fields that store the simple form of the rational number (you can calculate the simple form by deviding both numerator and denominator by their greatest common devisor, see point 1.4)
    4. calculate the simple form of a rational by dividing both numerator and denominator by the [greatest common divisor](https://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid.27s_algorithm). Hint: make this `gcd` calculation a private function.
2. Write the constructor that only accepts a numerator and sets the denominator to `1` by default. What is the simplest way to do this?
3. Implement the `toString` method such that `new Rational(a, b).toString == a/b` and `new Rational(a, 1).toString == a`
4. Implement the `equals` method
5. Implement a method `def +(that: Rational): Rational` that takes another `Rational` and returns another `Rational` that is the sum of `this` and `other`
6. Implement a method `def +(i: Int): Rational` that takes an `Int` and returns the sum of `this` and `i`
7. Repeat step 5 and 6 for `-`, `*` and `/`
8. Write some equations with your `Rational` implementation and see how it works
9. Why would it **not** be useful to have `Rational` be a case class?
10. If you feel fancy, implement some more operators such as *inversion*, *exponentiation*, *comparison operators such as `<`, `>`, `<=`, `>=`, `==` and `!=`* or implement a method that represents a `Rational` in [another representation](https://en.wikipedia.org/wiki/Rational_number#Other_representations).
