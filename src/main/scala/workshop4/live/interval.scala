package workshop4.live

import rx.lang.scala.Observable

import scala.concurrent.duration._
import scala.io.StdIn
import scala.language.postfixOps

object interval extends App {

  Observable.interval(1 second)
    .doOnSubscribe(println(Thread.currentThread().getName))

    .subscribe(_ => println("got something"), e => e.printStackTrace(), () => println("completed"))

  StdIn.readLine()
}
