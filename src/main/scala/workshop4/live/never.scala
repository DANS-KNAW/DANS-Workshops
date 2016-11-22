package workshop4.live

import rx.lang.scala.Observable

import scala.io.StdIn

object never extends App {

  Observable.never
    .doOnSubscribe(println(Thread.currentThread().getName))
    .subscribe(_ => println("got something"), e => e.printStackTrace(), () => println("completed"))

  StdIn.readLine()
}
