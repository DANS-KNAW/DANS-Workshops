# Logger
In this assignment we will create a modular logger that is based on multiple-inheritance.

1. Create a trait `Logger` that has a single method `log` with parameter `msg: String` and return type `Unit`.
2. Create a class `ConsoleLogger` that implements the `Logger` trait by printing `msg` onto the console.
3. In some cases we want the logger to also log the thread you're working on. Implement a trait `ThreadLogging` that does this. Make sure that the following code works correctly:

    ```scala
    val logger = new ConsoleLogger with ThreadLogging
    logger.log("Hello world") // should print [<current thread>] Hello world
    ```
    
4. In other cases you might want to have some date/time info. Implement another trait `DateTimeLogging`. You may use `System.currentTimeMillis()` for reading the time; if you're feeling fancy give something like the Java8 Date/Time API or JodaTime a try!
5. Play around with these four traits/classes and see how they operate together. How does the order of extension alter the behavior of the logger? Also try to reason and understand why it has this behavior.
6. Got some spare time? Extend this logging API with some more useful extension modules, add more methods to the `Logger` trait such as `debug`, `error`, `info`, etc., add other kinds of logger (`FileLogger` for example) and see how everything can mix and match together. Just have fun hacking!
