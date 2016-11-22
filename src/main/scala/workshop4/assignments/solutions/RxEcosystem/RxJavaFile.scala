package workshop4.assignments.solutions.RxEcosystem

import java.io.File

import com.github.davidmoten.rx.FileObservable
import rx.lang.scala.JavaConverters._
import rx.lang.scala.Observable

import scala.concurrent.duration._
import scala.io.StdIn
import scala.language.postfixOps

object TextFileTailer extends App {

  /*
    Tails a text file using utf8 encoding, starting to read from the beginning of the file and with a sample speed of 250 millis.
    It uses a standard java NIO source to check whether the file is updated.

      1. This one appears to be rather slow, and looking in the source code, it doesn't take the sampleTimeMs into account.
      2. Also, you always got to have an empty line at the end of the file for a new event to occur.
      3. Creates a new event for every line, even if you have multiple lines that were added since your last save/write action.
   */
  val tailedText: Observable[String] = FileObservable.tailer()
    .file(new File("target/test/rxfileutils/textfiletailer.txt"))
    .startPosition(0L)
    .sampleTimeMs(250L)
    .utf8()
    .tailText()
    .asScala

  tailedText.subscribe(
    text => println(s"  > $text"),
    _.printStackTrace,
    () => println("completed")
  )
}

object NoNIOFileTailer extends App {

  /*
    More responsive version of the one above. This one uses a timer as its source and polls every 250 millis to see whether there
    are changes to the observed file.
   */
  val tailedText: Observable[String] = FileObservable.tailer()
    .file(new File("target/test/rxfileutils/noniofiletailer.txt"))
    .source(Observable.interval(250 milliseconds).asJava)
    .tailText()
    .asScala

  tailedText.subscribe(
    text => println(s"  > $text"),
    _.printStackTrace,
    () => println("completed")
  )

  StdIn.readLine()
}

object WatchService extends App {

  import java.nio.file.Path
  import java.nio.file.StandardWatchEventKinds._

  /*
    Watches a directory or file for MODIFY, CREATE and DELETE events
   */
  val dir = new File("target/test/rxfileutils/")
  val path = dir.toPath.toAbsolutePath
  val watchLog = FileObservable.from(dir, ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE).asScala
    .map(evt => s"${evt.kind().name()} event occured with value ${path.resolve(evt.context().asInstanceOf[Path])} and count ${evt.count()}")

  watchLog.subscribe(
    evt => println(s"  > $evt"),
    _.printStackTrace,
    () => println("completed")
  )
}
