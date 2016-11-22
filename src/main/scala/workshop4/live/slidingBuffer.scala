package workshop4.live

import rx.lang.scala.Observable

object slidingBuffer extends App {

  Observable.from(1 to 10)
    .slidingBuffer(2, 1) // gives lists of 2 elements, except for the last one, which is a list of 1 element
    .filter(_.size == 2) // filter out the 'event' with 1 element
    .subscribe(println(_))
}
