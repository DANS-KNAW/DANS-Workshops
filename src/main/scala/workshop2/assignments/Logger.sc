import java.time.LocalDateTime
import java.time.format.{DateTimeFormatter, FormatStyle}

// 1. Create trait `Logger` with method `log`
trait Logger {
  def log(msg: String): Unit
}

// 2. Create the `ConsoleLogger` class
class ConsoleLogger extends Logger {
  def log(msg: String) = println(msg)
}

// 3. Create the `ThreadLogger` trait. Notice the `abstract override` keywords and the `super` call
trait ThreadLogging extends Logger {
  abstract override def log(msg: String) = {
    super.log(s"[${Thread.currentThread().getName}] $msg")
  }
}

// 4. `DateTimeLogging`. I used the Java8 DateTime API for this; other implementations are of course possible here.
trait DateTimeLogging extends Logger {

  val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

  abstract override def log(msg: String) = {
    super.log(s"[${LocalDateTime.now.format(formatter)}] $msg")
  }
}

// 5. Play time!
val log1 = new ConsoleLogger
log1.log("hello world")

val log2 = new ConsoleLogger with ThreadLogging
log2.log("hello world")

val log3 = new ConsoleLogger with DateTimeLogging
log3.log("hello world")

val log4 = new ConsoleLogger with ThreadLogging with DateTimeLogging
log4.log("hello world")

val log5 = new ConsoleLogger with DateTimeLogging with ThreadLogging
log5.log("hello world")

// 6. See the Logger.scala file
