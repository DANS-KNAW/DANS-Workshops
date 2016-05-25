<!-- inspired by Scala for the Impatient -->
# UnitConversion
1. Create an object `Conversions` with methods inchesToCentimeters, gallonsToLiters and milesToKilometers, all of type `Double => Double`, meaning that the methods take one `Double` as a parameter and return a `Double`. For the conversion factors, see [this Wikipedia page](https://en.wikipedia.org/wiki/Conversion_of_units).
2. This object is not really practical: if you have no write access to the source code, you cannot extend the functionality of this object. Also, notice that in this example all conversions just multiply the input with a conversion factor.
 Create a general superclass `UnitConversion` with one `apply` method and define **objects** `InchesToCentimeters`, `GallonsToLiters` and `MilesToKilometers` that extend `UnitConversion`. In the end, make sure that you can evaluate something like `InchesToCentimeters(20.5)` to `52.07`.
